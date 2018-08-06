package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetExchRateInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetNewTransactionsFromScheduledInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetTransactionsByAccountIdInteractor
import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput

class MainScreenPresenter(view: IMainScreenFragmentInput,
                          var getAccounts: GetAccountsInteractor,
                          val getExchRateInteractor: GetExchRateInteractor,
                          val getNewTransactionsFromScheduledInteractor: GetNewTransactionsFromScheduledInteractor,
                          val getTransactionsByAccountIdInteractor: GetTransactionsByAccountIdInteractor,
                          router: IMainActivityRouterInput,
                          var pieChart: IPieChartViewInput? = null) :
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
                Currency.USD -> "%.2f".format(course)
                else -> ""
            })

            getAccounts.executeFlowable {
                if (it.isNotEmpty()) {
                    view?.showBalance(response.Valute.USD.Value, it[0])
                }
            }
        }
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
            pieChart?.changeData(it.toList())
        }
    }

    fun changeCurrentAccount(account: Account) {
        view?.showBalance(account)
        view?.showBalance(course, account)
        changePieChart(account)
    }

    fun getAccountsId(): Array<Int> {
        return arrayOf()
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