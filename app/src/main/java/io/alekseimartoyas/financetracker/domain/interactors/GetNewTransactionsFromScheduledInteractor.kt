package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GetNewTransactionsFromScheduledInteractor(private val dataSource: IDataSourceInput,
                                                job: Scheduler = Schedulers.io(),
                                                ui: Scheduler = AndroidSchedulers.mainThread())
    : BaseInteractor<List<FinanceTransaction>, Unit>(job, ui) {

    override fun buildFlowable(args: Unit?): Flowable<List<FinanceTransaction>>? {
        return dataSource.getNewTransactionsFromScheduled()
    }
}