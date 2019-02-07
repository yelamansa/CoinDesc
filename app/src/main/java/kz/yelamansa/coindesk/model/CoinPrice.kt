package kz.yelamansa.coindesk.model

data class CoinPrice(
    val bpi: Map<String, Float>,
    val disclaimer: String,
    val time: Time
)