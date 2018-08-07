package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter

import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput


class AccountListPresenter(view: IAccountListFragment,
                           var getAccounts: GetAccountsInteractor,
                           router: IMainActivityRouterInput) :
        BasePresenter<IAccountListFragment, IMainActivityRouterInput>(view, router) {

    override fun onStart() {

    }

    override fun onStop() {

    }
}