package io.alekseimartoyas.financetracker.presentation.modules.history.presenter

import io.alekseimartoyas.financetracker.domain.interactors.GetDoneTransactionsInteractor
import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.history.view.RecyclerViewManager.TransactionRVAdapter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput

class HistoryPresenter(view: IHistoryFragmentInput,
                       val getDoneTransactionsInteractor: GetDoneTransactionsInteractor,
                       router: IMainActivityRouterInput,
                       var adapter: ITransactionRVInput? = null) :
        BasePresenter<IHistoryFragmentInput,
                IMainActivityRouterInput>(view, router) {

    fun getAdapter(): TransactionRVAdapter = adapter!! as TransactionRVAdapter

    override fun onStart() {
        getDoneTransactionsInteractor.executeFlowable {
            view?.showTransactions(it)
        }
    }

    override fun onStop() {

    }

}