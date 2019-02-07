package kz.yelamansa.coindesk.view.chart

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.coin_chart_fragment.*
import kz.yelamansa.coindesk.App
import kz.yelamansa.coindesk.R
import kz.yelamansa.coindesk.presenter.CoinChartPresenter
import javax.inject.Inject
import java.text.SimpleDateFormat


class CoinChartFragment : Fragment(), CoinChartView {

    @Inject lateinit var presenter:CoinChartPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.coin_chart_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity?.application as App).appComponent.inject(this)
        super.onViewCreated(view, savedInstanceState)

        presenter.attachView(this)

        ArrayAdapter.createFromResource(
            activity,
            R.array.currencies,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            currencySpinner.adapter = adapter
        }
        currencySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                 updateLineChart()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        ArrayAdapter.createFromResource(
            activity,
            R.array.periods,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            periodSpinner.adapter = adapter
        }
        periodSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, pos: Int, id: Long) {
                updateLineChart()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    fun updateLineChart(){
        presenter.getCoinPrices(periodSpinner.selectedItemId, currencySpinner.selectedItem.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    private var referenceTimestamp = 0L

    override fun updateCoinPrices(bpis: Map<String, Float>) {
        val entries: MutableList<Entry> = mutableListOf()

        for (date in bpis) {
            var timeStamp = SimpleDateFormat("yyyy-MM-dd").parse(date.key).time

            if (referenceTimestamp==0L) referenceTimestamp = timeStamp

            timeStamp -= referenceTimestamp

            entries.add(Entry(timeStamp.toFloat(), date.value))
        }

        drawLineChart(entries)
    }

    private fun drawLineChart(entries: MutableList<Entry>){

        val xAxisFormatter = DateAxisValueFormatter(referenceTimestamp, periodSpinner.selectedItemId.toInt())
        val xAxis = lineChart.xAxis
        xAxis.valueFormatter = xAxisFormatter

        val myMarkerView = DateMarkerView(activity as Context, R.layout.date_marker_view_layout, referenceTimestamp)
        lineChart.marker = myMarkerView

        val dataSet =  LineDataSet(entries, "")
        lineChart.data = LineData(dataSet)
        lineChart.animateXY(1500, 1500)
        lineChart.invalidate()
    }

    override fun showError(error: String) {
        Toast.makeText(activity, error, Toast.LENGTH_SHORT).show()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        lineChart.visibility = View.GONE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
        lineChart.visibility = View.VISIBLE
    }

}

