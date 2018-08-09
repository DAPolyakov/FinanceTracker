package io.alekseimartoyas.financetracker.presentation.modules.statistics.configurator

import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetTransactionsInteractor
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.presentation.modules.statistics.presenter.StatisticsPresenter
import io.alekseimartoyas.financetracker.presentation.modules.statistics.view.StatisticsFragment


class StatisticsConfigurator {

    fun buildModule(view: StatisticsFragment) {
        val presenter = StatisticsPresenter(view,
                GetTransactionsInteractor(App.graph.getDataSource()),
                GetAccountsInteractor(App.graph.getDataSource()),
                view.activity as IMainActivityRouterInput)
        view.setPres(presenter)
    }
}