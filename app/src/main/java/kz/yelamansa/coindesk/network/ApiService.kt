package kz.yelamansa.coindesk.network

import io.reactivex.Single
import kz.yelamansa.coindesk.model.CoinPrice
import kz.yelamansa.coindesk.model.Rate
import kz.yelamansa.coindesk.model.Transaction
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("https://api.coindesk.com/v1/bpi/historical/close.json")
    fun getCoinPrices(@Query("start") startDate:String,
                  @Query("end") endDate:String,
                  @Query("currency") currency: String):Single<Response<CoinPrice>>

    @GET("https://www.bitstamp.net/api/transactions/")
    fun getTransactions():Single<Response<List<Transaction>>>

    @GET("https://api.coindesk.com/v1/bpi/currentprice/{currency}.json")
    fun getRateByCurrency(@Path("currency") currency: String): Single<Response<Rate>>
}