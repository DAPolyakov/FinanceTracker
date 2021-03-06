package io.alekseimartoyas.financetracker.presentation.modules.templates.configurator

import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.domain.interactors.GetTemplateTransactionsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.UpdateFinanceTransactionInteractor
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.financetracker.presentation.modules.templates.presenter.TemplatesPresenter
import io.alekseimartoyas.financetracker.presentation.modules.templates.view.TemplatesFragment


class TemplatesConfigurator {

    fun buildModule(view: TemplatesFragment) {
        val presenter = TemplatesPresenter(view,
                GetTemplateTransactionsInteractor(App.graph.getDataSource()),
                UpdateFinanceTransactionInteractor(App.graph.getDataSource()),
                view.activity as IMainActivityRouterInput)
        view.setPres(presenter)
    }
}