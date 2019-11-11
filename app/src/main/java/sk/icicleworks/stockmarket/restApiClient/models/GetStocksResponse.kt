package sk.icicleworks.stockmarket.restApiClient.models

import android.util.Log
import java.util.*
import kotlin.collections.ArrayList
import org.json.JSONObject
import org.json.JSONException
import java.time.LocalDate
import java.time.LocalDateTime

class GetStocksResponse constructor() {
    val metaData : MetaData = MetaData()
    val data : ArrayList<StockData> = ArrayList()

    fun parseData(jsonData : String){
        try {
            val jsonObject = JSONObject(jsonData)

            metaData.symbol = jsonObject.getJSONObject("Meta Data").getString(("2. Symbol"))

            val time = jsonObject.getJSONObject("Time Series (5min)")
            val iterator = time.keys()
            while (iterator.hasNext()) {
                val stockData = StockData()

                val date = iterator.next().toString()

                val dateJson = time.getJSONObject(date)
                stockData.open = dateJson.getString("1. open").toDouble()
                stockData.high = dateJson.getString("2. high").toDouble()
                stockData.low = dateJson.getString("3. low").toDouble()
                stockData.close = dateJson.getString("4. close").toDouble()
                stockData.volume = dateJson.getString("5. volume").toInt()
                stockData.time = LocalDateTime.parse(date.replace(" ", "T"))

                data.add(stockData)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }
}

class MetaData {
    var information : String = ""
    var symbol : String = ""
    var lastRefreshed : String = ""
    var outputSize : String = ""
    var timeZone : String = ""
}

class StockData {
    var time : LocalDateTime = LocalDateTime.now()
    var open : Double = 0.0
    var high : Double = 0.0
    var low  : Double = 0.0
    var close : Double = 0.0
    var volume : Int = 0
}