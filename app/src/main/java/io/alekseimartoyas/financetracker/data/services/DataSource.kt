package io.alekseimartoyas.financetracker.data.services

import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.R
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.OperationType
import io.alekseimartoyas.financetracker.domain.dinversion.IDataSourceInput
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class DataSource() : IDataSourceInput {

    private val db = App.db

    override val subjectFakeAccounts: Subject<Array<Account>> = BehaviorSubject.create()
    override val currentAccount = BehaviorSubject.create<Account>()

    val fakeAccounts = ArrayList<Account>()
    val fakeTransactions = ArrayList<FinanceTransaction>()

    init {
        createFakeAccounts()
    }

    override fun addTransaction(transaction: FinanceTransaction): Observable<Boolean> {
        fakeTransactions.add(transaction)
        return Observable.just(true)
    }

    override fun addTransactions(transactions: Array<FinanceTransaction>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransaction(): FinanceTransaction {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTransactionsByAccountId(accountId: Long): Flowable<Array<FinanceTransaction>> {
//        db.financeTransactionDao.getByAccountId(accountId)
        return Flowable.fromArray(fakeTransactions.filter { it.accountId == accountId }.toTypedArray())
    }

    override fun getAccounts(): Observable<Array<Account>> {
        return Observable.fromArray(fakeAccounts.toTypedArray())
    }

    override fun changeCurrentAccount(account: Account): Completable {
        currentAccount.onNext(account)
        return Completable.complete()
    }

    private fun createFakeAccounts() {

        fakeTransactions.add(FinanceTransaction(OperationType.DEBIT, 10f, Currency.RUB, CategoryType.Category1, "12-01-2010", 1, 1))
        fakeTransactions.add(FinanceTransaction(OperationType.ENLISTMENT, 10f, Currency.RUB, CategoryType.Category2, "15-04-2012", 1, 2))
        fakeTransactions.add(FinanceTransaction(OperationType.DEBIT, 10f, Currency.RUB, CategoryType.Category3, "24-08-2014", 1, 3))
        fakeTransactions.add(FinanceTransaction(OperationType.DEBIT, 10f, Currency.RUB, CategoryType.Category1, "12-01-2010", 2, 1))
        fakeTransactions.add(FinanceTransaction(OperationType.DEBIT, 10f, Currency.RUB, CategoryType.Category3, "24-08-2014", 2, 3))
        fakeTransactions.add(FinanceTransaction(OperationType.ENLISTMENT, 10f, Currency.RUB, CategoryType.Category2, "15-04-2012", 3, 2))

        fakeAccounts.add(Account(R.string.petr, Currency.RUB, 100.toBigDecimal(), 1))
        fakeAccounts.add(Account(R.string.maria, Currency.RUB, 200.toBigDecimal(), 2))
        fakeAccounts.add(Account(R.string.alex, Currency.USD, 300.toBigDecimal(), 3))

        currentAccount.onNext(fakeAccounts[0])
    }

}