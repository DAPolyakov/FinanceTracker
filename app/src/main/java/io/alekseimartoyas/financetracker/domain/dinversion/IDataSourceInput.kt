package io.alekseimartoyas.financetracker.domain.dinversion

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.Transaction
import io.reactivex.Observable

interface IDataSourceInput {
    fun addTransaction(transaction: Transaction)

    fun addTransactions(transactions: Array<Transaction>)

    fun getTransaction(): Transaction

    fun getTransactions(): Array<Transaction>

    fun getAccounts(): Observable<Array<Account>>
}