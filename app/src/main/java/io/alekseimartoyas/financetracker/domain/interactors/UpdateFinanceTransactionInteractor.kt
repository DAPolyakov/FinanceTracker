package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UpdateFinanceTransactionInteractor(private val dataSource: IDataSourceInput) : BaseInteractor<Unit, FinanceTransaction>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()) {

    override fun buildFlowable(parametr: FinanceTransaction?): Flowable<Unit> =
            dataSource.updateFinanceTransaction(parametr!!)

}