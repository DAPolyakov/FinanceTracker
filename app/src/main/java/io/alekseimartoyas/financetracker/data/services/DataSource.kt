package io.alekseimartoyas.financetracker.data.services

import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.Transaction
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Observable

class DataSource : IDataSourceInput {

    val fakeAccounts = ArrayList<Account>()

    init {
        createFakeAccounts()
    }

    override fun addTransaction(transaction: Transaction) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTransactions(transactions: Array<Transaction>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransaction(): Transaction {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactions(): Array<Transaction> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getAccounts(): Observable<Array<Account>> {
        return Observable.fromArray(fakeAccounts.toTypedArray())
    }

    private fun createFakeAccounts() {
        val transactions = ArrayList<Transaction>()
        transactions.add(Transaction(1, OperationType.DEBIT, 10f, Currency.RUB, CategoryType.Category1, "12-01-2010"))
        transactions.add(Transaction(2, OperationType.ENLISTMENT, 10f, Currency.RUB, CategoryType.Category2, "15-04-2012"))
        transactions.add(Transaction(3, OperationType.DEBIT, 10f, Currency.RUB, CategoryType.Category3, "24-08-2014"))

        fakeAccounts.add(Account("1", R.string.petr, Currency.RUB, transactions))
        fakeAccounts.add(Account("2", R.string.maria, Currency.RUB, transactions))
        fakeAccounts.add(Account("3", R.string.alex, Currency.USD, transactions))
    }

}