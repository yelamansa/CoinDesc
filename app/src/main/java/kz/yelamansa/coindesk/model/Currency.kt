package kz.yelamansa.coindesk.model

data class Currency(
    val code: String,
    val description: String,
    val rate: String,
    val rate_float: Double
)