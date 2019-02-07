package kz.yelamansa.coindesk.view

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kz.yelamansa.coindesk.R
import kz.yelamansa.coindesk.view.chart.CoinChartFragment
import kz.yelamansa.coindesk.view.converter.ConverterFragment
import kz.yelamansa.coindesk.view.transactions.TransactionsFragment

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_chart -> {
                replaceFragment(
                    CoinChartFragment(),
                    R.id.frame_layout
                )
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_transactions -> {
                replaceFragment(TransactionsFragment(), R.id.frame_layout)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_converter -> {
                replaceFragment(ConverterFragment(), R.id.frame_layout)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(CoinChartFragment(), R.id.frame_layout)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}


inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int) {
    supportFragmentManager.inTransaction{replace(frameId, fragment)}
}