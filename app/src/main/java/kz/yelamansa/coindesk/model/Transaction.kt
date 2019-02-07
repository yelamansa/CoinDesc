package kz.yelamansa.coindesk.model

data class Transaction(
    val amount: String,
    val date: String,
    val price: String,
    val tid: Int,
    val type: Int
)