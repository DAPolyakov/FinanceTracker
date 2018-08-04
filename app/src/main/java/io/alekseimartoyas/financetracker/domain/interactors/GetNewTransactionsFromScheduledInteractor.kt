package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GetNewTransactionsFromScheduledInteractor(private val dataSource: IDataSourceInput) : BaseInteractor<List<FinanceTransaction>, Unit>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()) {

    override fun buildFlowable(parametr: Unit?): Flowable<List<FinanceTransaction>>? {
        return dataSource.getNewTransactionsFromScheduled()
    }
}