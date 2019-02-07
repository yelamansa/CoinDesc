package kz.yelamansa.coindesk.di

import dagger.Component
import kz.yelamansa.coindesk.view.chart.CoinChartFragment
import kz.yelamansa.coindesk.view.converter.ConverterFragment
import kz.yelamansa.coindesk.view.transactions.TransactionsFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, AppModule::class])
interface AppComponent {
    fun inject(coinChartFragment: CoinChartFragment)
    fun inject(transactionsFragment: TransactionsFragment)
    fun inject(converterFragment: ConverterFragment)
}