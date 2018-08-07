package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.configurator

import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.AccountListPresenter
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.AccountListFragment
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput


class AccountListConfigurator {

    fun buildModule(view: AccountListFragment) {
        val presenter = AccountListPresenter(view,
                GetAccountsInteractor(App.graph.getDataSource()),
                view.activity as IMainActivityRouterInput)

        view.setPres(presenter)
    }
}