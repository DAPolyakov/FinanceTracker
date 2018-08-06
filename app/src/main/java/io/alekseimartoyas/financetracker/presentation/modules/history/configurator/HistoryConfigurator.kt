package io.alekseimartoyas.financetracker.presentation.modules.history.configurator

import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.domain.interactors.GetDoneTransactionsInteractor
import io.alekseimartoyas.financetracker.presentation.modules.history.presenter.HistoryPresenter
import io.alekseimartoyas.financetracker.presentation.modules.history.view.HistoryFragment
import io.alekseimartoyas.financetracker.presentation.modules.history.view.RecyclerViewManager.TransactionRVAdapter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput

class HistoryConfigurator {

    fun buildModule(view: HistoryFragment) {
        val presenter = HistoryPresenter(view,
                GetDoneTransactionsInteractor(App.graph.getDataSource()),
                view.activity as IMainActivityRouterInput,
                TransactionRVAdapter())

        view.setPres(presenter)
    }
}