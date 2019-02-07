package kz.yelamansa.coindesk.presenter

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.yelamansa.coindesk.network.ApiService
import kz.yelamansa.coindesk.view.converter.ConverterView
import java.text.FieldPosition

class ConverterPresenter(val apiService:ApiService) {

    var view:ConverterView? = null

    fun attachView(view: ConverterView){
        this.view = view
    }

    fun detachView(){
        view = null
    }

    @SuppressLint("CheckResult")
    fun convert(value: Double, currency:String, position: Int){
        apiService.getRateByCurrency(currency)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showLoading() }
            .doFinally{ view?.hideLoading() }
            .subscribe({
                if (it.isSuccessful){
                    val rate: Double? = it.body().bpi[currency]?.rate_float

                    when(position){
                        0 -> {
                            if (rate != null) {
                                view?.updateCalculatedRates(value / rate, position)
                            }
                        }
                        1->{
                            if(rate != null){
                                view?.updateCalculatedRates(value*rate, position)
                            }
                        }
                    }
                }else{
                    view?.showError(it.errorBody().string())
                }
            },
                {})
    }

}