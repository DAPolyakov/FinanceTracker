package io.alekseimartoyas.financetracker.data.services

import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Flowable
import java.math.BigDecimal

class DataSource : IDataSourceInput {

    override fun getScheduledTransactions(): Flowable<List<FinanceTransaction>> {
        return db.financeTransactionDao.getAllScheduled()
    }

    private val db = App.db

    override fun addTransaction(transaction: FinanceTransaction, sum: BigDecimal): Flowable<Unit> {
        return Flowable.fromCallable {
            db.accountFinanceTransactionDao.insertFinanceTransactionUpdateAccountAmount(
                    transaction,
                    transaction.accountId,
                    sum)
        }
    }

    override fun getTransactions(): Flowable<List<FinanceTransaction>> {
        return db.financeTransactionDao.getAll()
    }

    override fun getDoneTransactions(): Flowable<List<FinanceTransaction>> {
        return db.financeTransactionDao.getAllDone()
    }

    override fun getTransactionsByAccountId(accountId: Long): Flowable<List<FinanceTransaction>> {
        return db.financeTransactionDao.getByAccountId(accountId)
    }

    override fun getAccounts(): Flowable<List<Account>> {
        return db.accountDao.getAll()
    }

    override fun getNewTransactionsFromScheduled(): Flowable<List<FinanceTransaction>> {
        return db.financeTransactionDao.getNewTransactionsFromScheduled(System.currentTimeMillis())
    }

    override fun updateFinanceTransaction(financeTransaction: FinanceTransaction,
                                          sum: BigDecimal): Flowable<Unit> {
        return Flowable.fromCallable {
            db.accountFinanceTransactionDao.updateFinanceTransactionUpdateAccountAmount(
                    financeTransaction,
                    financeTransaction.accountId,
                    sum)
        }
    }
}