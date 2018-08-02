package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GetAccountsInteractor(private val dataSource: IDataSourceInput) : BaseInteractor<Array<Account>, Unit>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()) {

    override fun buildObservable(parametr: Unit?): Observable<Array<Account>> =
            dataSource.getAccounts()

}