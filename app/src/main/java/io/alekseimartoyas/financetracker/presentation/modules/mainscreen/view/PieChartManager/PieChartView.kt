package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.PieChartManager

import android.graphics.Color
import android.support.v4.content.ContextCompat
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.IPieChartViewInput

class PieChartView(chart: PieChart) : IPieChartViewInput {
    private var chart: PieChart? = chart
    private lateinit var chartData: List<FinanceTransaction>

    override fun changeData(data: List<FinanceTransaction>) {
        chartData = data
        setup()
    }

    private fun setup() {
        val pieData = hashMapOf<String, Float>()

        for (item in chartData) {
            val title = chart!!.context.getString(item.category.strId)

            if (pieData[title] == null) {
                pieData[title] = item.quantity
            } else {
                pieData[title] = (pieData[title] ?: 0F) + item.quantity
            }
        }

        val pieEntries = mutableListOf<PieEntry>()

        val colors = listOf(
                ContextCompat.getColor(chart!!.context, R.color.yandex_blue),
                ContextCompat.getColor(chart!!.context, R.color.yandex_orange),
                ContextCompat.getColor(chart!!.context, R.color.yandex_red))

        val textColors = listOf(
                ContextCompat.getColor(chart!!.context, R.color.yandex_white),
                ContextCompat.getColor(chart!!.context, R.color.yandex_blue),
                ContextCompat.getColor(chart!!.context, R.color.yandex_orange))

        for (item in pieData) {
            val value = PieEntry(item.value, item.key)
            pieEntries.add(value)
//            colors.add(getColor(item.key))
        }

        val dataSet = PieDataSet(pieEntries, "")
        dataSet.colors = colors
        dataSet.valueTextSize = 18f
//        dataSet.setValueTextColors(textColors)
        dataSet.valueTextColor = Color.WHITE
        val data = PieData(dataSet)

        chart!!.apply {
            this.data = data
            setDrawEntryLabels(false)
            description?.isEnabled = false
            setUsePercentValues(true)

            legend?.apply {
                isWordWrapEnabled = false
                textSize = 12f
                position = Legend.LegendPosition.LEFT_OF_CHART
            }
            invalidate()
            notifyDataSetChanged()
        }
    }

    override fun destructor() {
        chart = null
    }
}