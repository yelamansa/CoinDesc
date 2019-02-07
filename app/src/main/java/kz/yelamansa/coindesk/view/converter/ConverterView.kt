package kz.yelamansa.coindesk.view.converter

interface ConverterView {
    fun updateCalculatedRates(convertedValue:Double, position:Int)
    fun showError(error:String)
    fun showLoading()
    fun hideLoading()
}