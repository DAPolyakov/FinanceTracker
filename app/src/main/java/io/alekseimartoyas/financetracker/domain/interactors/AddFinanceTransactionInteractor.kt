package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class AddFinanceTransactionInteractor(private val dataSource: IDataSourceInput) : BaseInteractor<Boolean, FinanceTransaction>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()) {

    override fun buildObservable(parametr: FinanceTransaction?): Observable<Boolean> =
            dataSource.addTransaction(parametr!!)

}