package sk.icicleworks.stockmarket.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import sk.icicleworks.stockmarket.database.entity.PinnedStockWithData

@Dao
interface PinnedStockWithDataDao {
    @Query("SELECT * FROM pinnedStocks")
    fun getAll() : LiveData<List<PinnedStockWithData>>
}