package io.alekseimartoyas.financetracker.domain.dinversion

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.Subject

interface IDataSourceInput {

    val subjectFakeAccounts: Subject<Array<Account>>
    val currentAccount: Subject<Account>

    fun addTransaction(transaction: FinanceTransaction)

    fun addTransactions(transactions: Array<FinanceTransaction>)

    fun getTransaction(): FinanceTransaction

    fun getTransactionsByAccountId(accountId: Long): Flowable<Array<FinanceTransaction>>

    fun getAccounts(): Observable<Array<Account>>

    fun changeCurrentAccount(account: Account): Completable
}