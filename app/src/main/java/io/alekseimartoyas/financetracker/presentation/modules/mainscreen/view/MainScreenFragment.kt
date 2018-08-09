package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.configurator.MainScreenConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.IMainScreenFragmentInput
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.MainScreenPresenter
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.PieChartManager.PieChartView
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.SpinnerManager.AccountSpinnerArrayAdapter
import io.alekseimartoyas.financetracker.utils.isTabletMode
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
        pieChart = PieChartView(pie_chart_view)

        MainScreenConfigurator().buildModule(this)

        add_transaction_fab.setOnClickListener {
            presenter?.showAddTransaction()
        }

        initViews(view.context)
    }

    private fun initViews(context: Context) {
        if (context.isTabletMode()) {
            spinner_account.visibility = View.GONE
            divider.visibility = View.VISIBLE

            rvAccounts?.apply {
                visibility = View.VISIBLE
                layoutManager = LinearLayoutManager(context, LinearLayout.VERTICAL, true)
                adapter = AccountRvAdapter {
                    presenter?.changeCurrentAccount(it)
                }
            }
        } else {
            rvAccounts.visibility = View.GONE
            divider.visibility = View.GONE

            spinner_account.visibility = View.VISIBLE
            spinner_account?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                    presenter?.changeCurrentAccount(spinner_account.selectedItem as Account)
                }
            }
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
        if (context!!.isTabletMode()) {
            (rvAccounts.adapter as AccountRvAdapter).setData(accounts)
            presenter!!.changeCurrentAccount(accounts[0])
        } else {
            spinner_account?.adapter = AccountSpinnerArrayAdapter(context!!, accounts)
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