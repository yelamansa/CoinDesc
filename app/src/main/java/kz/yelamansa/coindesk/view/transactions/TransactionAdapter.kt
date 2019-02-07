package kz.yelamansa.coindesk.view.transactions

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_transaction.view.*
import kz.yelamansa.coindesk.R
import kz.yelamansa.coindesk.model.Transaction
import java.text.SimpleDateFormat
import java.util.*

class TransactionAdapter(val items: List<Transaction>): RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): TransactionViewHolder {
        return TransactionViewHolder(
            LayoutInflater.from(p0.context).inflate(
                R.layout.item_transaction,
                p0,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.itemView.amountTextView.text = "${items[position].amount} BTC"
        holder.itemView.dateTextView.text = getSimpleDate(items[position].date)
        if(items[position].type==0) {
            holder.itemView.typeTextView.text = "Покупка"
            holder.itemView.arrowImageView.rotation = 90f
        } else {
            holder.itemView.typeTextView.text = "Продажа"
            holder.itemView.arrowImageView.rotation = -90f
        }
     }

    override fun getItemCount() = items.size

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private fun getSimpleDate(timeStampStr:String): String{
        val date = Date(timeStampStr.toLong()*1000)
        return SimpleDateFormat("dd.MM.yyyy HH:mm").format(date)
    }
}