package sk.icicleworks.stockmarket.database.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import sk.icicleworks.stockmarket.database.entity.PinnedStock

@Dao
interface PinnedStocksDao {
    @Insert
    fun insert(pinnedStock: PinnedStock)

    @Query("DELETE FROM pinnedStocks WHERE stockId == :stockId")
    fun delete(stockId : String)

    @Query("SELECT * FROM pinnedStocks")
    fun getAll(): LiveData<List<PinnedStock>>

    @Query("SELECT * FROM pinnedStocks WHERE stockId == :stockId")
    fun getById(stockId: String): LiveData<PinnedStock>
}