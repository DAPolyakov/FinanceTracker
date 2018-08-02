package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.presentation.modules.anothercurrency.view.AnotherCurrencyFragment
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.configurator.MainScreenConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.IMainScreenFragmentInput
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.MainScreenPresenter
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.SpinnerManager.AccountSpinnerArrayAdapter
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
        if (savedInstanceState == null) {
            activity?.apply {
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.anoth_curr_card,
                                AnotherCurrencyFragment(),
                                "visible_sub_fragment")
                        .commit()
            }
        }
    }

    fun setAddAccountBtListener() {
        add_account_bt.setOnClickListener {
            //            presenter?.showAddAccount()
        }
    }

    override fun showAccountsList(accounts: Array<Account>) {
        spinner_accounts?.adapter = AccountSpinnerArrayAdapter(context!!, accounts)
    }

    override fun showBalance(account: Account) {
        main_currency.text = getString(account.currency.strId)
        main_quant_text.text = "${account.amount}"

    }

    override fun onStart() {
        super.onStart()
        presenter?.onStart()
        presenter?.getAccountsId()
        presenter?.getAccountData(/*selected*/1)
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