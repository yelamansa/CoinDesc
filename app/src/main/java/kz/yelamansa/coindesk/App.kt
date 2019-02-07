package kz.yelamansa.coindesk

import android.app.Application
import kz.yelamansa.coindesk.di.AppComponent
import kz.yelamansa.coindesk.di.AppModule
import kz.yelamansa.coindesk.di.DaggerAppComponent
import kz.yelamansa.coindesk.di.NetworkModule


class App:Application() {
    lateinit var appComponent:AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger()
    }

    private fun initDagger():AppComponent =
         DaggerAppComponent
             .builder()
             .networkModule(NetworkModule())
             .appModule(AppModule())
             .build()
}