package kz.yelamansa.coindesk.view.transactions

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_transactions.*
import kz.yelamansa.coindesk.App
import kz.yelamansa.coindesk.R
import kz.yelamansa.coindesk.model.Transaction
import kz.yelamansa.coindesk.presenter.TransactionsPresenter
import javax.inject.Inject

class TransactionsFragment:Fragment(), TransactionsVeiw {

    @Inject lateinit var presenter: TransactionsPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_transactions, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
        presenter.loadTransactions()
    }

    override fun updateTransactions(transactions: List<Transaction>) {
        transactionRecyclerView?.layoutManager = LinearLayoutManager(activity)
        transactionRecyclerView?.adapter = TransactionAdapter(activity as Context, transactions)
        transactionRecyclerView?.addItemDecoration(DividerItemDecoration(transactionRecyclerView.context, LinearLayoutManager.VERTICAL))
    }

    override fun showError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE

    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }
}