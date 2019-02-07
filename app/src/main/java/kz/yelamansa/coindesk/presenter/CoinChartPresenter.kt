package kz.yelamansa.coindesk.presenter

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.yelamansa.coindesk.network.ApiService
import kz.yelamansa.coindesk.view.chart.CoinChartView
import java.text.SimpleDateFormat
import java.util.*

class CoinChartPresenter(private var apiService:ApiService){
    var view: CoinChartView? = null

    fun attachView(coinChartView: CoinChartView){
        view = coinChartView
    }

    fun detachView(){
        view = null
    }

    @SuppressLint("CheckResult")
    fun getCoinPrices(period:Long, currency:String) {
        apiService.getCoinPrices(getStartDate(period), getEndDate(), currency)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showLoading() }
            .doFinally { view?.hideLoading() }
            .subscribe({
                if (it.isSuccessful) {
                    it.body()?.let {
                        print(it)
                        view?.updateCoinPrices(it.bpi)
                    }
                } else {
                    view?.showError(it.errorBody().string())
                }
            }, {
                view?.showError("Пожалуйста, попробуйте еще раз")
            })
    }

    private fun getEndDate() = SimpleDateFormat("yyyy-MM-dd").format(Date())

    private fun getStartDate(period: Long):String{
        val dayInMs = (1000 * 60 * 60 * 24).toLong()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")

        return when(period){
            0L ->{
                simpleDateFormat.format(Date(System.currentTimeMillis() - (7*dayInMs)))
            }
            1L ->{
                simpleDateFormat.format(Date(System.currentTimeMillis() - (30*dayInMs)))
            }
            3L ->{
                simpleDateFormat.format(Date(System.currentTimeMillis() - (365*dayInMs)))
            }
            else -> simpleDateFormat.format(Date(System.currentTimeMillis() - (7*dayInMs)))
        }
    }
}