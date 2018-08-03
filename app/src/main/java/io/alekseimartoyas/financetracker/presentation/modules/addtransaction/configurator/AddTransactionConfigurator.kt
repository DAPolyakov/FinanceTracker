package io.alekseimartoyas.financetracker.presentation.modules.addtransaction.configurator

import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.domain.interactors.AddFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.presenter.AddTransactionPresenter
import io.alekseimartoyas.financetracker.presentation.modules.addtransaction.view.AddTransactionActivity


class AddTransactionConfigurator {

    fun buildModule(view: AddTransactionActivity) {
        val presenter = AddTransactionPresenter(view,
                AddFinanceTransactionInteractor(App.graph.getDataSource()),
                GetAccountsInteractor(App.graph.getDataSource()))

        view.setPres(presenter)
    }
}