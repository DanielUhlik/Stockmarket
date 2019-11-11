package sk.icicleworks.stockmarket.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "stockData", primaryKeys = arrayOf("stockId", "dateTime"))
data class StockData (
    @ColumnInfo(name = "stockId") val stockId : String,
    @ColumnInfo(name = "dateTime") val timeString : String,
    @ColumnInfo(name = "open") val open : Double,
    @ColumnInfo(name = "high") val high : Double,
    @ColumnInfo(name = "low") val low  : Double,
    @ColumnInfo(name = "close") val close : Double,
    @ColumnInfo(name = "volume") val volume : Int
){
    val dateTime : LocalDateTime
        get() = LocalDateTime.parse(timeString)
}