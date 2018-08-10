package io.alekseimartoyas.financetracker.presentation.modules.addtransaction.configurator

import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.domain.interactors.*
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter.AddTransactionPresenter
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.view.AddTransactionActivity


class AddTransactionConfigurator() {

    fun buildModule(view: AddTransactionActivity) {
        val presenter = AddTransactionPresenter(view,
                UpdateFinanceTransactionInteractor(App.graph.getDataSource()),
                GetExchRateInteractor(App.graph.getExchRateProvider()),
                AddFinanceTransactionInteractor(App.graph.getDataSource()),
                GetAccountsInteractor(App.graph.getDataSource()),
                GetTemplateTransactionsInteractor(App.graph.getDataSource()))

        view.setPres(presenter)
    }
}