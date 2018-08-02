package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter

import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.tradetracker.Foundation.BasePresenter

class MainScreenPresenter(view: IMainScreenFragmentInput,
                          var getAccounts: GetAccountsInteractor,
                          router: IMainActivityRouterInput,
                          var pieChart: IPieChartViewInput? = null) :
        BasePresenter<IMainScreenFragmentInput,
                IMainActivityRouterInput>(view, router) {

    override fun onStart() {
//        pieChart?.changeData(listOf(
//                Transaction(1,
//                        OperationType.ENLISTMENT,
//                        75F,
//                        Currency.USD,
//                        "correcting",
//                        "yesterday"),
//                Transaction(2,
//                        OperationType.DEBIT,
//                        25F,
//                        Currency.USD,
//                        "Food",
//                        "today")))


        getAccounts.execute {
            view?.showAccountsList(it)
        }
    }

    fun showAddAccount() {
        router?.showAddAccount()
    }

    fun getAccountsId(): Array<Int> {
        return arrayOf()
    }

    fun getAccountData(accountId: Int) {

    }

    override fun onStop() {
        pieChart?.destructor()
    }

    override fun destructor() {
        super.destructor()
        view = null
        pieChart?.destructor()
    }
}