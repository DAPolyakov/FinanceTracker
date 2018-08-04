package io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.presenter

import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.interactors.GetScheduledTransactionsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.UpdateFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.view.RecyclerViewManager.ScheduledTransactionsRVAdapter
import java.math.BigDecimal

class ScheduledTransactionsPresenter(view: IScheduledTransactionsInput,
                                     val getScheduledTransactionsInteractor: GetScheduledTransactionsInteractor,
                                     val updateFinanceTransactionInteractor: UpdateFinanceTransactionInteractor,
                                     router: IMainActivityRouterInput,
                                     var adapter: IScheduledTransactionsRVInput? = null) :
        BasePresenter<IScheduledTransactionsInput,
                IMainActivityRouterInput>(view, router) {

    fun getAdapter(): ScheduledTransactionsRVAdapter = adapter!! as ScheduledTransactionsRVAdapter

    override fun onStart() {
        getScheduledTransactionsInteractor.executeFlowable {
            adapter?.setData(it.toTypedArray())
        }

        adapter?.onDelete {
            updateFinanceTransactionInteractor.executeFlowable(Pair(it.copy(state = FinanceTransactionState.Canceled), BigDecimal(0))) {
                
            }
        }
    }

    override fun onStop() {

    }
}