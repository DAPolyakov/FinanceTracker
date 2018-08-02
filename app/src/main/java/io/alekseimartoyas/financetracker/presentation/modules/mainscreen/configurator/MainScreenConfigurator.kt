package io.alekseimartoyas.financetracker.presentation.modules.mainscreen.configurator

import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetExchRateInteractor
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.presenter.MainScreenPresenter
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.MainScreenFragment
import io.alekseimartoyas.financetracker.presentation.modules.mainscreen.view.PieChartManager.PieChartView
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import kotlinx.android.synthetic.main.fragment_main_screen.*

class MainScreenConfigurator {

    fun buildModule(view: MainScreenFragment) {
        val presenter = MainScreenPresenter(view,
                GetAccountsInteractor(App.graph.getDataSource()),
                view.activity as IMainActivityRouterInput,
                PieChartView(view.pie_chart_view))

        view.setPres(presenter)
    }
}