package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GetTransactionsByAccountIdInteractor(private val dataSource: IDataSourceInput) :
        BaseInteractor<List<FinanceTransaction>,
                Long>(
                Schedulers.io(),
                AndroidSchedulers.mainThread()) {

    override fun buildFlowable(parametr: Long?): Flowable<List<FinanceTransaction>>? {
        return dataSource.getTransactionsByAccountId(parametr!!)
    }
}