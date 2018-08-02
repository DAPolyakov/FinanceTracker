package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ChangeCurrentAccountInteractor(private val dataSource: IDataSourceInput) : BaseInteractor<Any, Account>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()) {

    override fun buildObservable(account: Account?): Observable<Any> =
            dataSource.changeCurrentAccount(account!!).toObservable()

}