package io.alekseimartoyas.financetracker.presentation.modules.templates.presenter

import io.alekseimartoyas.financetracker.domain.interactors.GetTransactionsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.UpdateFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.presentation.modules.templates.view.TemplatesRvAdapter
import io.alekseimartoyas.financetracker.presentation.modules.templates.view.TemplatesView


class TemplatesPresenter(view: TemplatesView,
                         val getScheduledTransactionsInteractor: GetTransactionsInteractor,
                         val updateFinanceTransactionInteractor: UpdateFinanceTransactionInteractor,
                         router: IMainActivityRouterInput) :
        BasePresenter<TemplatesView,
                IMainActivityRouterInput>(view, router) {

    lateinit var adapter: TemplatesRvAdapter

    fun getRvAdapter(): TemplatesRvAdapter = adapter

    override fun onStart() {

        adapter = TemplatesRvAdapter(onClick = {
            router?.showAddTransaction(it)
        }, onDelete = { it ->
            //            updateFinanceTransactionInteractor
//                    .executeFlowable(Pair(it.copy(state = FinanceTransactionState.Canceled), BigDecimal(0))) {
//                    }
        })

        getScheduledTransactionsInteractor.executeFlowable {
            adapter.setData(it.toTypedArray())
        }

        view!!.setRvAdapter(adapter)

//        adapter?.onDelete { it ->
//            updateFinanceTransactionInteractor
//                    .executeFlowable(Pair(it.copy(state = FinanceTransactionState.Canceled), BigDecimal(0))) {
//                    }
//        }

    }


    override fun onStop() {

    }
}