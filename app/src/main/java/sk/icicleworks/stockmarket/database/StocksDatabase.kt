package sk.icicleworks.stockmarket.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import sk.icicleworks.stockmarket.database.DAO.PinnedStocksDao
import sk.icicleworks.stockmarket.database.entity.PinnedStock
import androidx.room.Room
import sk.icicleworks.stockmarket.database.DAO.PinnedStockWithDataDao
import sk.icicleworks.stockmarket.database.DAO.StockDataDao
import sk.icicleworks.stockmarket.database.entity.StockData

@Database(entities = [PinnedStock::class, StockData::class], version = 1)
abstract class StocksDatabase : RoomDatabase() {
    abstract fun PinnedStocksDao(): PinnedStocksDao
    abstract fun StockDataDao() : StockDataDao
    abstract fun PinnedStockWithDataDao() : PinnedStockWithDataDao

    companion object {
        private var INSTANCE: StocksDatabase? = null

        internal fun getDatabase(context: Context): StocksDatabase {
            if (INSTANCE == null) {
                synchronized(StocksDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            StocksDatabase::class.java, "stock_database"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}