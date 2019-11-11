package sk.icicleworks.stockmarket.database.entity

import androidx.room.Embedded
import androidx.room.Relation


class PinnedStockWithData {
    @Embedded
    var pinnedStock: PinnedStock? = null

    @Relation(parentColumn = "stockId", entityColumn = "stockId", entity = StockData::class)
    var data: List<StockData>? = null
}