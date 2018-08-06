package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.network.DailyResponse
import io.alekseimartoyas.financetracker.data.services.ExchRateProvider
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetExchRateInteractor(private val exchRateProvider: ExchRateProvider) :
        BaseInteractor<DailyResponse,
                Unit>(
                Schedulers.io(),
                AndroidSchedulers.mainThread()) {

    override fun buildObservable(parametr: Unit?): Observable<DailyResponse> =
            exchRateProvider.getExchRates()
}