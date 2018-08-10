package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.network.DailyResponse
import io.alekseimartoyas.financetracker.data.services.ExchRateProvider
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class GetExchRateInteractor(private val exchRateProvider: ExchRateProvider,
                            job: Scheduler = Schedulers.io(),
                            ui: Scheduler = AndroidSchedulers.mainThread()) :
        BaseInteractor<DailyResponse, Unit>(job, ui) {

    override fun buildObservable(args: Unit?): Observable<DailyResponse> =
            exchRateProvider.getExchRates()
}