package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GetDoneTransactionsInteractor(private val dataSource: IDataSourceInput,
                                    job: Scheduler = Schedulers.io(),
                                    ui: Scheduler = AndroidSchedulers.mainThread()) :
        BaseInteractor<List<FinanceTransaction>, Void>(job, ui) {

    override fun buildFlowable(args: Void?): Flowable<List<FinanceTransaction>>? {
        return dataSource.getDoneTransactions()
    }
}