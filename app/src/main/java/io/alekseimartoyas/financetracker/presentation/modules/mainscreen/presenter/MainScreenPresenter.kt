package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetExchRateInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetNewTransactionsFromScheduledInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetTransactionsByAccountIdInteractor
import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import java.text.DecimalFormat

class MainScreenPresenter(view: IMainScreenFragmentInput,
                          var getAccounts: GetAccountsInteractor,
                          val getExchRateInteractor: GetExchRateInteractor,
                          val getNewTransactionsFromScheduledInteractor: GetNewTransactionsFromScheduledInteractor,
                          val getTransactionsByAccountIdInteractor: GetTransactionsByAccountIdInteractor,
                          router: IMainActivityRouterInput) :
        BasePresenter<IMainScreenFragmentInput,
                IMainActivityRouterInput>(view, router) {

    private var course: Double = 1.0

    override fun onStart() {

        getAccounts.executeFlowable {
            view?.showAccountsList(it.toTypedArray())
            if (it.isNotEmpty()) {
                view?.showBalance(it[0])
            }
        }

        getExchRateInteractor.execute { response ->

            course = response.Valute.USD.Value

            view?.setExchRate(when (Currency.USD) {
                Currency.USD -> DecimalFormat("0.00").format(course)
                else -> ""
            })
        }
    }

    fun showAddTransaction() {
        router?.showAddTransaction()
    }

    fun checkScheduledTransactions() {
        getNewTransactionsFromScheduledInteractor.executeFlowable {
            if (it.isNotEmpty()) {
                router?.showAddTransaction(it.first())
            }
        }
    }

    private fun changePieChart(account: Account) {
        getTransactionsByAccountIdInteractor.executeFlowable(account.id) {
            view?.changePieChartData(it)
        }
    }

    fun changeCurrentAccount(account: Account) {
        view?.showBalance(account)
        view?.showBalance(course, account)
        changePieChart(account)
    }

    override fun onStop() {
    }

    override fun destructor() {
        super.destructor()
        view = null
    }
}