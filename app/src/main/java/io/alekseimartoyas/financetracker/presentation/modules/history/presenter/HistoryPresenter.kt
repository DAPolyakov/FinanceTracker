package io.alekseimartoyas.financetracker.presentation.modules.history.presenter

import io.alekseimartoyas.financetracker.domain.interactors.GetTransactionsInteractor
import io.alekseimartoyas.financetracker.presentation.modules.history.view.RecyclerViewManager.TransactionRVAdapter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.tradetracker.Foundation.BasePresenter

class HistoryPresenter(view: IHistoryFragmentInput,
                       val getTransactionsInteractor: GetTransactionsInteractor,
                       router: IMainActivityRouterInput,
                       var adapter: ITransactionRVInput? = null) :
        BasePresenter<IHistoryFragmentInput,
                IMainActivityRouterInput>(view, router) {

    fun showAddTransaction() {
        router?.showAddTransaction()
    }

    fun getAdapter(): TransactionRVAdapter = adapter!! as TransactionRVAdapter

    override fun onStart() {
        getTransactionsInteractor.executeFlowable {
            adapter?.setData(it.toTypedArray())
        }
    }

    override fun onStop() {

    }
}