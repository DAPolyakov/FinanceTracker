package io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.configurator

import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.domain.interactors.GetScheduledTransactionsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.UpdateFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.presenter.ScheduledTransactionsPresenter
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.view.RecyclerViewManager.ScheduledTransactionsRVAdapter
import io.alekseimartoyas.financetracker.presentation.modules.scheduledtransactions.view.ScheduledTransactionsFragment


class ScheduledTransactionsConfigurator {

    fun buildModule(view: ScheduledTransactionsFragment) {
        val presenter = ScheduledTransactionsPresenter(view,
                GetScheduledTransactionsInteractor(App.graph.getDataSource()),
                UpdateFinanceTransactionInteractor(App.graph.getDataSource()),
                view.activity as IMainActivityRouterInput,
                ScheduledTransactionsRVAdapter())
        view.setPres(presenter)
    }
}