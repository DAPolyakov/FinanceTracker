package io.alekseimartoyas.financetracker.domain.dinversion

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.Transaction
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.Subject

interface IDataSourceInput {

    val subjectFakeAccounts: Subject<Array<Account>>
    val currentAccount: Subject<Account>

    fun addTransaction(transaction: Transaction)

    fun addTransactions(transactions: Array<Transaction>)

    fun getTransaction(): Transaction

    fun getTransactions(): Array<Transaction>

    fun getAccounts(): Observable<Array<Account>>

    fun changeCurrentAccount(account: Account): Completable
}