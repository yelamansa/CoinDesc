package kz.yelamansa.coindesk.di

import dagger.Module
import dagger.Provides
import kz.yelamansa.coindesk.network.ApiService
import kz.yelamansa.coindesk.presenter.CoinChartPresenter
import kz.yelamansa.coindesk.presenter.ConverterPresenter
import kz.yelamansa.coindesk.presenter.TransactionsPresenter

@Module
class AppModule {


    @Provides
    fun provideCoinChartPresenter(apiService:ApiService):CoinChartPresenter = CoinChartPresenter(apiService)

    @Provides
    fun provideTransactionPresenter(apiService: ApiService):TransactionsPresenter = TransactionsPresenter(apiService)

    @Provides
    fun provideConverterPresenter(apiService: ApiService):ConverterPresenter = ConverterPresenter(apiService)

}