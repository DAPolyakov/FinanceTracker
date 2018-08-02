package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetExchRateInteractor
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.tradetracker.Foundation.BasePresenter

class MainScreenPresenter(view: IMainScreenFragmentInput,
                          var getAccounts: GetAccountsInteractor,
                          val getExchRateInteractor: GetExchRateInteractor,
                          router: IMainActivityRouterInput,
                          var pieChart: IPieChartViewInput? = null) :
        BasePresenter<IMainScreenFragmentInput,
                IMainActivityRouterInput>(view, router) {

    private var course: Double = 1.0

    override fun onStart() {
        getAccounts.execute {
            view?.showAccountsList(it)
            view?.showBalance(it[0])
        }

        getExchRateInteractor.execute { response ->

            course = response.Valute.USD.Value

            view?.setExchRate(when (Currency.USD) {
                Currency.USD -> "%.2f".format(course)
                else -> ""
            })

            getAccounts.execute {
                view?.showBalance(response.Valute.USD.Value, it[0])
            }
        }
    }

    private fun changePieChart(account: Account) {
        pieChart?.changeData(account.transactions)
    }

    fun changeCurrentAccount(account: Account) {
        view?.showBalance(account)
        view?.showBalance(course, account)
        changePieChart(account)
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