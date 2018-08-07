package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.configurator.AccountListConfigurator
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.AccountListPresenter
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.IAccountListFragment
import io.alekseimartoyas.tradetracker.Foundation.BaseFragment


class AccountListFragment : BaseFragment<AccountListPresenter>(),
        IAccountListFragment {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View = inflater.inflate(R.layout.fragment_list_accounts, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        AccountListConfigurator().buildModule(this)
    }

}