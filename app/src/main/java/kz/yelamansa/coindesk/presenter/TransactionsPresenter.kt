package kz.yelamansa.coindesk.presenter

import android.annotation.SuppressLint
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kz.yelamansa.coindesk.network.ApiService
import kz.yelamansa.coindesk.view.transactions.TransactionsVeiw

class TransactionsPresenter(val apiService: ApiService){

    var view: TransactionsVeiw? = null

    fun attachView(view: TransactionsVeiw){
        this.view = view
    }

    fun detachView(){
        view = null
    }

    @SuppressLint("CheckResult")
    fun loadTransactions(){
        apiService.getTransactions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { view?.showLoading() }
            .doFinally{view?.hideLoading()}
            .subscribe({
                if (it.isSuccessful){
                    view?.updateTransactions(it.body())
                }else view?.showError(it.errorBody().string())
            }, {
                view?.showError("Пожалуйста, попробуйте еще раз")
            })
    }
}