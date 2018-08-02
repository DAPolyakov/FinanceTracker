package io.alekseimartoyas.financetracker.presentation.modules.anothercurrency.presenter

import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.interactors.GetAccountsInteractor
import io.alekseimartoyas.financetracker.domain.interactors.GetExchRateInteractor
import io.alekseimartoyas.financetracker.presentation.modules.navigationdrawer.router.IMainActivityRouterInput
import io.alekseimartoyas.tradetracker.Foundation.BasePresenter

class AnotherCurrencyPresenter(val getExchRateInteractor: GetExchRateInteractor,
                               var getAccounts: GetAccountsInteractor,
                               view: IAnotherCurrencyFragmentInput,
                               router: IMainActivityRouterInput) :
        BasePresenter<IAnotherCurrencyFragmentInput,
                IMainActivityRouterInput>(view, router) {

//    private val ExchRate

    override fun onStart() {
        getExchRateInteractor.execute { response ->
            //убрать отсюда
            //пусть interactor возвращает String по параметру

            view?.setExchRate(when (Currency.USD) {
                Currency.USD -> "%.2f".format(response.Valute.USD.Value)
                else -> ""
            })

            getAccounts.execute {
                view?.showBalance(response.Valute.USD.Value, it[0])
            }
        }

    }

    override fun onStop() {
        getExchRateInteractor.dispose()
    }
}