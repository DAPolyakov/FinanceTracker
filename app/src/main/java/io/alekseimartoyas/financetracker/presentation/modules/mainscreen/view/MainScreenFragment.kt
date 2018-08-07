package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.configurator.MainScreenConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.IMainScreenFragmentInput
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.MainScreenPresenter
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.PieChartManager.PieChartView
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.SpinnerManager.AccountSpinnerArrayAdapter
import io.alekseimartoyas.financetracker.utils.toTargetCurrency
import io.alekseimartoyas.tradetracker.Foundation.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_screen.*
import java.text.DecimalFormat

class MainScreenFragment : BaseFragment<MainScreenPresenter>(),
        IMainScreenFragmentInput {

    lateinit var pieChart: PieChartView

    private val decimalFormat = DecimalFormat("0.00")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pieChart = PieChartView(pie_chart_view)

        MainScreenConfigurator().buildModule(this)

        add_transaction_fab.setOnClickListener {
            presenter?.showAddTransaction()
        }
    }

    override fun setExchRate(data: String) {
        val s = "$data ${resources.getString(R.string.RUB)}"
        dollar_rate.text = s
    }

    override fun onResume() {
        super.onResume()
        presenter?.checkScheduledTransactions()
    }

    override fun showBalance(course: Double, account: Account) {
        val targetCurrency = when (account.currency) {
            Currency.RUB -> Currency.USD
            Currency.USD -> Currency.RUB
        }
        val secondCurrencyValue = account.currency.toTargetCurrency(targetCurrency, account.amount, course.toBigDecimal())
        val formatSecondCurrencyValue = decimalFormat.format(secondCurrencyValue)
        val s = "($formatSecondCurrencyValue ${getString(targetCurrency.strId)})"
        usd_curr_chang_text.text = s
    }

    override fun showAccountsList(accounts: Array<Account>) {
        spinner_account?.adapter = AccountSpinnerArrayAdapter(context!!, accounts)

        spinner_account?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                presenter?.changeCurrentAccount(spinner_account.selectedItem as Account)
            }

        }
    }

    override fun showBalance(account: Account) {
        main_currency.text = getString(account.currency.strId)
        main_quant_text.text = decimalFormat.format(account.amount)
    }

    override fun changePieChartData(transactions: List<FinanceTransaction>) {
        empty_pie_chart.visibility = if (transactions.isEmpty()) View.VISIBLE else View.GONE
        pieChart.changeData(transactions)
    }

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
        presenter?.getAccountsId()
    }

    override fun onDestroy() {
        super.onDestroy()
        pieChart.destructor()
        presenter?.destructor()
    }
}