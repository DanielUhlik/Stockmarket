package sk.icicleworks.stockmarket.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import sk.icicleworks.stockmarket.database.DAO.PinnedStockWithDataDao
import sk.icicleworks.stockmarket.database.DAO.PinnedStocksDao
import sk.icicleworks.stockmarket.database.DAO.StockDataDao
import sk.icicleworks.stockmarket.database.StocksDatabase
import sk.icicleworks.stockmarket.database.entity.PinnedStock
import sk.icicleworks.stockmarket.database.entity.PinnedStockWithData
import sk.icicleworks.stockmarket.database.entity.StockData
import sk.icicleworks.stockmarket.restApiClient.StocksApiClient
import sk.icicleworks.stockmarket.restApiClient.models.GetStocksResponse
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class StocksRepository (
    application: Application,
    private val pinnedStocksDao: PinnedStocksDao = StocksDatabase.getDatabase(application).PinnedStocksDao(),
    private val stockDataDao: StockDataDao = StocksDatabase.getDatabase(application).StockDataDao(),
    private val pinnedStockWithDataDao: PinnedStockWithDataDao = StocksDatabase.getDatabase(application).PinnedStockWithDataDao(),
    private val executor : ExecutorService = Executors.newSingleThreadExecutor()
) {

    init {
        reloadData()
    }

    fun getPinnedStocks() : LiveData<List<PinnedStock>> {
        return pinnedStocksDao.getAll()
    }

    fun getPinnedSTocksWithData() : LiveData<List<PinnedStockWithData>> {
        return pinnedStockWithDataDao.getAll()
    }

    fun addPinnedStock(pinnedStock: PinnedStock) {
        executor.execute { pinnedStocksDao.insert(pinnedStock) }
    }

    fun removePinnedStock(stockId: String){
        executor.execute { pinnedStocksDao.delete(stockId)}
    }

    fun reloadData(){
        getPinnedStocks().observeForever {
            executor.execute { it.forEach { refreshStockData(it.stockId) } }
        }
    }

    fun refreshStockData(stockId : String) {
        val data =  getDataFromApi(stockId)

        data.data.forEach {
            executor.execute { stockDataDao.insert(StockData(data.metaData.symbol, it.time.toString(), it.open, it.high, it.low, it.close, it.volume)) }
        }
    }

    fun getDataFromApi(stockId : String) : GetStocksResponse {
        val apiClient : StocksApiClient = StocksApiClient()
        val response = apiClient.getData(stockId).execute()

        val jsonData = response.body()?.charStream()!!.readText()
        val stockData = GetStocksResponse()
        stockData.parseData(jsonData)
        return stockData

    }

}