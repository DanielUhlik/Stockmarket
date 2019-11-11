package sk.icicleworks.stockmarket.restApiClient

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class StocksApiClient {

    private val api : StocksApi

    init {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val logInterceptor = HttpLoggingInterceptor()
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val clientBuilder = OkHttpClient().newBuilder()
            .addInterceptor(logInterceptor)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)

        val client = clientBuilder.build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.alphavantage.co/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()

            api = retrofit.create(StocksApi::class.java)
    }

    fun getData(stockId : String) : Call<ResponseBody> {
        return api.getData(symbol = stockId, apiKey = "8QX0XFPN725O16QQ") //TO-DO: Pridat apiKey do AppSettings
    }
}