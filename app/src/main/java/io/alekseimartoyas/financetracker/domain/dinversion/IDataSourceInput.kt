package io.alekseimartoyas.financetracker.domain.dinversion

import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.reactivex.Flowable
import java.math.BigDecimal

interface IDataSourceInput {

    fun addTransaction(transaction: FinanceTransaction, sum: BigDecimal): Flowable<Unit>

    fun getTransactions(): Flowable<List<FinanceTransaction>>

    fun getTransactionsByAccountId(accountId: Long): Flowable<List<FinanceTransaction>>

    fun getAccounts(): Flowable<List<Account>>

    fun getNewTransactionsFromScheduled(): Flowable<List<FinanceTransaction>>

    fun updateFinanceTransaction(
            financeTransaction: FinanceTransaction, sum: BigDecimal): Flowable<Unit>
}