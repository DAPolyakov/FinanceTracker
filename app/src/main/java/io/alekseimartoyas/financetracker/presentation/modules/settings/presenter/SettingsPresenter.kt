package io.alekseimartoyas.financetracker.presentation.modules.settings.presenter

import io.alekseimartoyas.financetracker.presentation.foundation.BasePresenter
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput

class SettingsPresenter(view: ISettingsActivityInput,
                        router: IMainActivityRouterInput?) :
        BasePresenter<ISettingsActivityInput,
                IMainActivityRouterInput>(view, router) {
    override fun onStart() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStop() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}