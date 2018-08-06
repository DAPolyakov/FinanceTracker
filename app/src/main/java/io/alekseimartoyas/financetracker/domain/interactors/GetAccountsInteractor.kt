package io.alekseimartoyas.financetracker.domain.interactors

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class GetAccountsInteractor(private val dataSource: IDataSourceInput) : BaseInteractor<List<Account>, Unit>(
        Schedulers.io(),
        AndroidSchedulers.mainThread()) {

    override fun buildFlowable(args: Unit?): Flowable<List<Account>>? {
        return dataSource.getAccounts()
    }
}