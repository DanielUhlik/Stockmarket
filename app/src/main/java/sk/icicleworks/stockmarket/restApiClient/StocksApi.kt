package sk.icicleworks.stockmarket.restApiClient

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StocksApi {

    @GET("query")
    fun getData(
        @Query("function") function : String = "TIME_SERIES_INTRADAY",
        @Query("interval") interval : String = "5min",
        @Query("apikey") apiKey : String = "",
        @Query("symbol") symbol : String
    ) : Call<ResponseBody>
}