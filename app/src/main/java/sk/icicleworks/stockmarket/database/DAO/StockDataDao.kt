package sk.icicleworks.stockmarket.database.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import sk.icicleworks.stockmarket.database.entity.StockData

@Dao
interface StockDataDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(stockData : StockData)
}