package sk.icicleworks.stockmarket.viewModels

import android.app.Application
import androidx.lifecycle.*
import sk.icicleworks.stockmarket.database.entity.PinnedStock
import sk.icicleworks.stockmarket.database.entity.PinnedStockWithData
import sk.icicleworks.stockmarket.repositories.StocksRepository

class MainActivityViewModel (
    application: Application
) : AndroidViewModel(application){

    private val stocksRepository : StocksRepository = StocksRepository(application)
    val getPinnedStockWithData : LiveData<List<PinnedStockWithData>> = stocksRepository.getPinnedSTocksWithData()

    fun addPinnedStock(stockId : String){
        val pinnedStock : PinnedStock = PinnedStock(stockId)
        stocksRepository.addPinnedStock(pinnedStock)
    }

    fun removePinnedStock(stockId: String){
        stocksRepository.removePinnedStock(stockId)
    }

}