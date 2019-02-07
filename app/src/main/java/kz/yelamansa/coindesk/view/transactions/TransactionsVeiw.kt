package kz.yelamansa.coindesk.view.transactions

import kz.yelamansa.coindesk.model.Transaction

interface TransactionsVeiw {
    fun updateTransactions(transactions:List<Transaction>)
    fun showError(error:String)
    fun showLoading()
    fun hideLoading()
}