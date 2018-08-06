package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.configurator.MainScreenConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.IMainScreenFragmentInput
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.MainScreenPresenter
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.SpinnerManager.AccountSpinnerArrayAdapter
import io.alekseimartoyas.financetracker.utils.toTargetCurrency
import io.alekseimartoyas.tradetracker.Foundation.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_screen.*

class MainScreenFragment : BaseFragment<MainScreenPresenter>(),
        IMainScreenFragmentInput {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainScreenConfigurator().buildModule(this)
        setAddAccountBtListener()
    }

    override fun setExchRate(data: String) {
        chang_rate_usd.text = "$data ${resources.getString(R.string.RUB)}"
    }

    override fun onResume() {
        super.onResume()
        presenter?.checkScheduledTransactions()
    }

    override fun showBalance(course: Double, account: Account) {
        val s = account.currency.toTargetCurrency(Currency.USD, account.amount, course.toBigDecimal()).toString()
        usd_curr_chang_text.text = s
    }

    fun setAddAccountBtListener() {
        add_account_bt.setOnClickListener {
            //            presenter?.showAddAccount()
        }
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
        main_quant_text.text = "${account.amount}"
    }

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
        presenter?.getAccountsId()
    }

    override fun onStop() {
        super.onStop()
        presenter?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destructor()
    }
}