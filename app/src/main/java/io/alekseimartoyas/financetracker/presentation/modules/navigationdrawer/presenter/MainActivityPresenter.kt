package io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.presenter

import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput

class MainActivityPresenter(
        view: IMainActivityInput,
        router: IMainActivityRouterInput?) :
        BasePresenter<IMainActivityInput,
                IMainActivityRouterInput>() {

    override fun onStart() {
    }

    override fun onStop() {
    }
}
