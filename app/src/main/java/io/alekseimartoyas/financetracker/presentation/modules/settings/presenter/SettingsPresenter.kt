package io.alekseimartoyas.financetracker.presentation.modules.settings.presenter

import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput

class SettingsPresenter(view: ISettingsActivityInput,
                        router: IMainActivityRouterInput?)
    : BasePresenter<ISettingsActivityInput, IMainActivityRouterInput>(view, router) {

    override fun onStart() {
    }

    override fun onStop() {
    }

}