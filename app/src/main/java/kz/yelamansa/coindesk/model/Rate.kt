package kz.yelamansa.coindesk.model

data class Rate(
    val bpi: Map<String, Currency>,
    val disclaimer: String,
    val time: TimeX
)