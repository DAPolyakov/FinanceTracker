package io.alekseimartoyas.financetracker.presentation.modules.statistics.view

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.StatisticsCategoryType
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.PieChartManager.PieChartView
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.SpinnerManager.AccountSpinnerArrayAdapter
import io.alekseimartoyas.financetracker.presentation.modules.statistics.configurator.StatisticsConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.statistics.presenter.StatisticsPresenter
import io.alekseimartoyas.tradetracker.Foundation.BaseFragment
import kotlinx.android.synthetic.main.fragment_statistics.*
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.*


class StatisticsFragment : BaseFragment<StatisticsPresenter>(),
        StatisticsView {

    private enum class CalendarRange { Start, Finish }

    private var dateInput = CalendarRange.Start
    lateinit var pieChart: PieChartView

    var myCalendar = Calendar.getInstance()

    var date: DatePickerDialog.OnDateSetListener = DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        updateLabel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
            inflater.inflate(R.layout.fragment_statistics, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pieChart = PieChartView(pie_chart_view)
        StatisticsConfigurator().buildModule(this)

        date_start.setOnClickListener { v ->
            dateInput = CalendarRange.Start
            DatePickerDialog(v.context, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        date_finish.setOnClickListener { v ->
            dateInput = CalendarRange.Finish
            DatePickerDialog(v.context, date, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
        }

        spinner_category!!.adapter = StatisticsArrayAdapter(view.context, StatisticsCategoryType.values())

        build_statistics!!.setOnClickListener {
            var dateStart = date_start.text.toString()
            var dateFinish = date_finish.text.toString()

            if (dateStart.isBlank()) dateStart = "01.01.1000"
            if (dateFinish.isBlank()) dateFinish = "31.12.9999"

            presenter!!.setFilter(
                    type = operation_type_spinner.selectedItem as String,
                    category = spinner_category.selectedItem as StatisticsCategoryType,
                    account = spinner_account.selectedItem as Account,
                    dateStart = dateStart,
                    dateFinish = dateFinish,
                    res = it.context.resources
            )
        }
    }

    override fun setAccountsList(accounts: ArrayList<Account>) {
        context?.let {
            accounts.add(0, Account(it.getString(R.string.all_accounts), Currency.RUB, BigDecimal(0)))
            spinner_account!!.adapter = AccountSpinnerArrayAdapter(it, accounts.toTypedArray())
        }
    }

    override fun showStatistics(transactions: List<FinanceTransaction>) {
        empty_pie_chart?.visibility = if (transactions.isEmpty()) View.VISIBLE else View.GONE
        pieChart.changeData(transactions)
    }

    private fun updateLabel() {
        val format = "dd.MM.yyyy"
        val sdf = SimpleDateFormat(format, Locale.US)
        when (dateInput) {
            StatisticsFragment.CalendarRange.Start -> date_start.setText(sdf.format(myCalendar.time))
            StatisticsFragment.CalendarRange.Finish -> date_finish.setText(sdf.format(myCalendar.time))
        }
    }

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter?.onStop()
    }
}