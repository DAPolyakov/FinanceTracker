package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers


class AddFinanceTransactionInteractor(private val dataSource: IDataSourceInput) : BaseInteractor<Unit, FinanceTransaction>(
        Schedulers.io(),
        Schedulers.io()) {

    override fun buildFlowable(parametr: FinanceTransaction?): Flowable<Unit> =
            dataSource.addTransaction(parametr!!)

}