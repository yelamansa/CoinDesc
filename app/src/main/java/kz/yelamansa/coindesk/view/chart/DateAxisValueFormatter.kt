package kz.yelamansa.coindesk.view.chart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class DateAxisValueFormatter(
    private val referenceTimestamp: Long // minimum timestamp in your data set
    , period: Int
) : IAxisValueFormatter {
    private var mDataFormat: DateFormat? = null
    private val mDate: Date = Date()

    init {
        when (period) {
            0, 1 -> this.mDataFormat = SimpleDateFormat("dd.MM", Locale.ENGLISH)
            2 -> this.mDataFormat = SimpleDateFormat("dd.MM.yyyy", Locale.ENGLISH)
            else -> this.mDataFormat = SimpleDateFormat("dd.MM", Locale.ENGLISH)
        }
    }

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        // convertedTimestamp = originalTimestamp - referenceTimestamp
        val convertedTimestamp = value.toLong()

        // Retrieve original timestamp
        val originalTimestamp = referenceTimestamp + convertedTimestamp

        // Convert timestamp to hour:minute
        return getDate(originalTimestamp)
    }

    private fun getDate(timestamp: Long): String {
        try {
            mDate.time = timestamp
            return mDataFormat!!.format(mDate)
        } catch (ex: Exception) {
            return "xx"
        }

    }
}