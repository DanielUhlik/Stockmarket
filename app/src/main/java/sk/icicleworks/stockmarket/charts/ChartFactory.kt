package sk.icicleworks.stockmarket.charts

import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlin.random.Random
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import sk.icicleworks.stockmarket.database.entity.StockData
import android.R.string.no
import android.R.attr.name
import java.time.format.DateTimeFormatter


object ChartFactory {
    fun initializeLineChart(chart : LineChart, data : List<StockData>){
        if (data.count() > 0) {
            val entries = ArrayList<Entry>()

            for ((index, value) in data.withIndex()) {
                entries.add(Entry(index.toFloat(), value.close.toFloat()))
            }

            val dataSet = LineDataSet(entries, "Close value")
            val lineData = LineData(dataSet)
            chart.data = lineData

            val formatter = object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase?): String {
                    return data[value.toInt()  % data.count()].dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                }
            }

            val xAxis = chart.xAxis
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.valueFormatter = formatter

            chart.invalidate()
        }
    }
}