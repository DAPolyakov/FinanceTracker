package io.alekseimartoyas.financetracker.presentation.modules.history.presenter

import io.alekseimartoyas.financetracker.data.local.Transaction
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.presentation.modules.history.view.RecyclerViewManager.TransactionRVAdapter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.tradetracker.Foundation.BasePresenter

class HistoryPresenter(view: IHistoryFragmentInput,
                       router: IMainActivityRouterInput,
                       var adapter: ITransactionRVInput? = null) :
        BasePresenter<IHistoryFragmentInput,
                IMainActivityRouterInput>(view, router) {

    fun showAddTransaction() {
        router?.showAddTransaction()
    }

    fun getAdapter(): TransactionRVAdapter = adapter!! as TransactionRVAdapter

    override fun onStart() {
        //from interactor
        adapter?.setData(arrayOf(
                Transaction(
                        OperationType.ENLISTMENT,
                        159F,
                        Currency.USD,
                        CategoryType.Category1,
                        "yesterday",
                        1),
                Transaction(
                        OperationType.DEBIT,
                        5F,
                        Currency.USD,
                        CategoryType.Category2,
                        "today",
                        2)))
    }

    override fun onStop() {

    }
}