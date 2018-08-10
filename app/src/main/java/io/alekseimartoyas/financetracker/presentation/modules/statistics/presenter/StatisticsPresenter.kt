package io.alekseimartoyas.financetracker.presentation.modules.statistics.presenter

import android.content.res.Resources
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.StatisticsCategoryType
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetTransactionsInteractor
import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.presentation.modules.statistics.view.StatisticsView


class StatisticsPresenter(view: StatisticsView,
                          val getTransactionsInteractor: GetTransactionsInteractor,
                          val getAccountsInteractor: GetAccountsInteractor,
                          router: IMainActivityRouterInput) :
        BasePresenter<StatisticsView, IMainActivityRouterInput>(view, router) {

    var transactions: List<FinanceTransaction> = emptyList()

    override fun onStart() {
        getAccountsInteractor.executeFlowable {
            view!!.setAccountsList(ArrayList(it))
        }

        getTransactionsInteractor.executeFlowable {
            transactions = it
            view!!.showStatistics(transactions)
        }
    }

    fun setFilter(type: String,
                  category: StatisticsCategoryType,
                  account: Account,
                  dateStart: String,
                  dateFinish: String,
                  res: Resources) {

        val operationType = when (type) {
            res.getString(R.string.enlistment) -> OperationType.ENLISTMENT
            else -> OperationType.DEBIT
        }

        val operationCategory = when (category) {
            StatisticsCategoryType.Education -> CategoryType.Education
            StatisticsCategoryType.Salary -> CategoryType.Salary
            StatisticsCategoryType.Shopping -> CategoryType.Shopping
            else -> CategoryType.Education
        }

        val data = transactions
                .asSequence()
                .filter {
                    if (type != res.getString(R.string.all_operation_type)) {
                        it.operationType == operationType
                    } else true
                }
                .filter {
                    if (category != StatisticsCategoryType.AllCategories) {
                        it.category == operationCategory
                    } else true
                }
                .filter {
                    if (account.id != null) {
                        it.accountId == account.id
                    } else true
                }
                .filter {
                    (getDateHash(it.date) >= getDateHash(dateStart))
                            && (getDateHash(it.date) <= getDateHash(dateFinish))
                }
                .toList()

        view!!.showStatistics(data)
    }

    private fun getDateHash(date: String): Int {
        val d = date.substring(0, 2).toInt()
        val m = date.substring(3, 5).toInt()
        val y = date.substring(6, 10).toInt()
        return y * 10000 + m * 100 + d
    }

    override fun onStop() {

    }
}