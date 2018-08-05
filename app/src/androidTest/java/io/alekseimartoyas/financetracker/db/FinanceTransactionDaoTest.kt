package io.alekseimartoyas.financetracker.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import io.alekseimartoyas.financetracker.data.local.AppDatabase
import io.alekseimartoyas.financetracker.data.local.FinanceTransaction
import io.alekseimartoyas.financetracker.data.local.FinanceTransactionDao
import io.alekseimartoyas.financetracker.domain.CategoryType
import io.alekseimartoyas.financetracker.domain.Currency
import io.alekseimartoyas.financetracker.domain.FinanceTransactionState
import io.alekseimartoyas.financetracker.domain.OperationType
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.TimeUnit


@RunWith(AndroidJUnit4::class)
class FinanceTransactionDaoTest {

    private val mockTransaction = FinanceTransaction(
            OperationType.DEBIT,
            100f,
            Currency.RUB,
            CategoryType.Category1,
            "01.10.2010",
            1,
            12341254,
            12341284,
            FinanceTransactionState.Done
    )

    private lateinit var db: AppDatabase
    private lateinit var dao: FinanceTransactionDao

    @Before
    @Throws(Exception::class)
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase::class.java)
                .build()
        dao = db.financeTransactionDao
    }

    @After
    @Throws(Exception::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun getAllAndInsertTransactions() {
        dao.getAll()
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.isEmpty()
                }
        val id = dao.insert(mockTransaction)
        dao.getAll()
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    (it.size == 1) && (mockTransaction.copy(id = id) == it[0])
                }
    }

    @Test
    @Throws(Exception::class)
    fun getAllDoneTransactions() {
        dao.insert(mockTransaction.copy(state = FinanceTransactionState.Waiting))
        dao.insert(mockTransaction.copy(state = FinanceTransactionState.Canceled))
        dao.getAllDone()
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.isEmpty()
                }
        val id = dao.insert(mockTransaction.copy(state = FinanceTransactionState.Done))
        dao.getAllDone()
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    (it.size == 1) && (mockTransaction.copy(state = FinanceTransactionState.Done, id = id) == it[0])
                }
    }

    @Test
    @Throws(Exception::class)
    fun getAllScheduled() {
        dao.insert(mockTransaction.copy(state = FinanceTransactionState.Done))
        dao.insert(mockTransaction.copy(state = FinanceTransactionState.Canceled))
        dao.getAllScheduled()
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.isEmpty()
                }
        val id = dao.insert(mockTransaction.copy(state = FinanceTransactionState.Waiting))
        dao.getAllScheduled()
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    (it.size == 1) && (mockTransaction.copy(state = FinanceTransactionState.Waiting, id = id) == it[0])
                }
    }

    @Test
    @Throws(Exception::class)
    fun getByAccountId() {
        val ids = arrayOf(1L, 2L, 3L)
        dao.insert(mockTransaction.copy(accountId = ids[0]))
        val id = dao.insert(mockTransaction.copy(accountId = ids[1]))
        dao.getByAccountId(ids[2])
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.isEmpty()
                }
        dao.getByAccountId(ids[1])
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    (it.size == 1) && (mockTransaction.copy(id = id, accountId = ids[1]) == it[0])
                }
    }

    @Test
    @Throws(Exception::class)
    fun getNewTransactionsFromScheduled() {
        val now = 100L
        val scheduledTransaction = mockTransaction.copy(state = FinanceTransactionState.Waiting, timeFinish = 20)
        val id = dao.insert(scheduledTransaction)
        dao.insert(mockTransaction.copy(state = FinanceTransactionState.Waiting, timeFinish = 200))
        dao.insert(mockTransaction.copy(state = FinanceTransactionState.Done, timeFinish = 20))
        dao.insert(mockTransaction.copy(state = FinanceTransactionState.Canceled, timeFinish = 20))
        dao.getNewTransactionsFromScheduled(now)
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    (it.size == 1) && (scheduledTransaction.copy(id = id) == it[0])
                }
    }


}