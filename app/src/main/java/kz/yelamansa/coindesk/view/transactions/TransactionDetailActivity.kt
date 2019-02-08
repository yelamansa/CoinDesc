package kz.yelamansa.coindesk.view.transactions

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.item_transaction.*
import kotlinx.android.synthetic.main.item_transaction.view.*
import kz.yelamansa.coindesk.R
import java.text.SimpleDateFormat
import java.util.*

const val TRANSACTION_AMOUNT:String = "TRANSACTION_ID"
const val TRANSACTION_DATE:String = "TRANSACTION_ID"
const val TRANSACTION_TYPE:String = "TRANSACTION_ID"

class TransactionDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bitcoinImageView.visibility = View.VISIBLE

        amountTextView.text = "${intent.getStringExtra(TRANSACTION_AMOUNT)} BTC"
        dateTextView.text = intent.getStringExtra(TRANSACTION_DATE)

        val type = intent.getIntExtra(TRANSACTION_TYPE, 0)
        if(type==0) {
            typeTextView.text = "Покупка"
            arrowImageView.rotation = 90f
        } else {
            typeTextView.text = "Продажа"
            arrowImageView.rotation = -90f
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
