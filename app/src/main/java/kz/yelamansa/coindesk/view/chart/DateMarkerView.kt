package kz.yelamansa.coindesk.view.chart

import android.content.Context
import android.widget.TextView

import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import kz.yelamansa.coindesk.R

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateMarkerView(
    context: Context, layoutResource: Int, private val referenceTimestamp: Long  // minimum timestamp in your data set
) : MarkerView(context, layoutResource) {

    private val tvContent: TextView = findViewById(R.id.contentTextView)
    private val mDataFormat: DateFormat
    private val mDate: Date

    private var mOffset: MPPointF? = null

    init {
        // this markerview only displays a textview
        this.mDataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        this.mDate = Date()
    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        val currentTimestamp = e!!.x.toInt() + referenceTimestamp

        tvContent.text = "${e.y} ${getTimedate(currentTimestamp)}" // set the entry-value as the display text
    }

    override fun getOffset(): MPPointF {
        if (mOffset == null) {
            // center the marker horizontally and vertically
            mOffset = MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }

        return mOffset as MPPointF
    }

    private fun getTimedate(timestamp: Long): String {
        return try {
            mDate.time = timestamp
            mDataFormat.format(mDate)
        } catch (ex: Exception) {
            "xx"
        }

    }


}