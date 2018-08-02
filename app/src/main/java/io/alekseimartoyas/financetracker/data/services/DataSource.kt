package io.alekseimartoyas.financetracker.data.services

import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.Transaction
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class DataSource : IDataSourceInput {

    override val subjectFakeAccounts: Subject<Array<Account>> = BehaviorSubject.create()
    override val currentAccount = BehaviorSubject.create<Account>()

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

    override fun changeCurrentAccount(account: Account): Completable {
        currentAccount.onNext(account)
        return Completable.complete()
    }

    private fun createFakeAccounts() {
        val transactions = ArrayList<Transaction>()
        transactions.add(Transaction(OperationType.DEBIT, 10f, Currency.RUB, CategoryType.Category1, "12-01-2010", 1))
        transactions.add(Transaction(OperationType.ENLISTMENT, 10f, Currency.RUB, CategoryType.Category2, "15-04-2012", 2))
        transactions.add(Transaction(OperationType.DEBIT, 10f, Currency.RUB, CategoryType.Category3, "24-08-2014", 3))

        val transactions2 = ArrayList<Transaction>()
        transactions2.add(Transaction(OperationType.DEBIT, 10f, Currency.RUB, CategoryType.Category1, "12-01-2010", 1))
        transactions2.add(Transaction(OperationType.DEBIT, 10f, Currency.RUB, CategoryType.Category3, "24-08-2014", 3))

        val transactions3 = ArrayList<Transaction>()
        transactions3.add(Transaction(OperationType.ENLISTMENT, 10f, Currency.RUB, CategoryType.Category2, "15-04-2012", 2))

        fakeAccounts.add(Account(R.string.petr, Currency.RUB, 100.toBigDecimal(), transactions, 1))
        fakeAccounts.add(Account(R.string.maria, Currency.RUB, 200.toBigDecimal(), transactions2, 2))
        fakeAccounts.add(Account(R.string.alex, Currency.USD, 300.toBigDecimal(), transactions3, 3))

        currentAccount.onNext(fakeAccounts[0])
    }

}