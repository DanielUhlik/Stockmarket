package sk.icicleworks.stockmarket.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import sk.icicleworks.stockmarket.restApiClient.models.GetStocksResponse

@Entity(tableName = "pinnedStocks")
data class PinnedStock(@PrimaryKey
    @ColumnInfo(name = "stockId") val stockId: String) {
    @Ignore
    var stockData: GetStocksResponse = GetStocksResponse()
}
