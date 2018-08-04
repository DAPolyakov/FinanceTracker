package io.alekseimartoyas.financetracker.domain.dinversion

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.reactivex.Flowable

interface IDataSourceInput {

    fun addTransaction(transaction: FinanceTransaction): Flowable<Unit>

    fun getTransactions(): Flowable<List<FinanceTransaction>>

    fun getTransactionsByAccountId(accountId: Long): Flowable<List<FinanceTransaction>>

    fun getAccounts(): Flowable<List<Account>>

    fun getNewTransactionsFromScheduled(): Flowable<List<FinanceTransaction>>

    fun updateFinanceTransaction(financeTransaction: FinanceTransaction): Flowable<Unit>
}