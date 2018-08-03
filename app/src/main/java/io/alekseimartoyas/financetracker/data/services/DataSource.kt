package io.alekseimartoyas.financetracker.data.services

import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Flowable
import io.reactivex.Observable

class DataSource() : IDataSourceInput {

    private val db = App.db

    override fun addTransaction(transaction: FinanceTransaction): Observable<Boolean> {
        db.financeTransactionDao.insert(transaction)
        return Observable.just(true)
    }

    override fun getTransactions(): Flowable<List<FinanceTransaction>> {
        return db.financeTransactionDao.getAll()
    }

    override fun getTransactionsByAccountId(accountId: Long): Flowable<List<FinanceTransaction>> {
        return db.financeTransactionDao.getByAccountId(accountId)
    }

    override fun getAccounts(): Flowable<List<Account>> {
        return db.accountDao.getAll()
    }

}