package io.alekseimartoyas.financetracker.presentation.modules.templates.presenter

import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.interactors.GetTemplateTransactionsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.UpdateFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.presentation.modules.templates.view.TemplatesRvAdapter
import io.alekseimartoyas.financetracker.presentation.modules.templates.view.TemplatesView
import java.math.BigDecimal


class TemplatesPresenter(view: TemplatesView,
                         val getTemplateTransactionsInteractor: GetTemplateTransactionsInteractor,
                         val updateFinanceTransactionInteractor: UpdateFinanceTransactionInteractor,
                         router: IMainActivityRouterInput) :
        BasePresenter<TemplatesView,
                IMainActivityRouterInput>(view, router) {

    lateinit var adapter: TemplatesRvAdapter

    override fun onStart() {

        adapter = TemplatesRvAdapter(onClick = {
            router?.showAddTransaction(it)
        }, onDelete = { it ->
            updateFinanceTransactionInteractor
                    .executeFlowable(Pair(it.copy(state = FinanceTransactionState.Canceled), BigDecimal(0))) {
                    }
        })

        getTemplateTransactionsInteractor.executeFlowable {
            adapter.setData(it.toTypedArray())
        }

        view!!.setRvAdapter(adapter)

    }


    override fun onStop() {

    }
}