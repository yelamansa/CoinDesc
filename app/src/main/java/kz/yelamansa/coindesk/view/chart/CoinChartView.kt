package kz.yelamansa.coindesk.view.chart

interface CoinChartView {
    fun updateCoinPrices(bpis: Map<String, Float>)
    fun showError(error:String)
    fun showLoading()
    fun hideLoading()
}