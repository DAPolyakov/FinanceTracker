package io.alekseimartoyas.financetracker.db

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.AccountDao
import io.alekseimartoyas.financetracker.data.local.AppDatabase
import io.alekseimartoyas.financetracker.domain.Currency
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class AccountDaoTest {

    private val mockAccount = Account(
            "Кошелек",
            Currency.RUB,
            BigDecimal(1000)
    )

    private lateinit var db: AppDatabase
    private lateinit var dao: AccountDao

    @Before
    @Throws(Exception::class)
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
                InstrumentationRegistry.getContext(),
                AppDatabase::class.java)
                .build()
        dao = db.accountDao
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
        val id = dao.insert(mockAccount)
        dao.getAll()
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    (it.size == 1) && (mockAccount.copy(id = id) == it[0])
                }

        dao.insert(mockAccount)
        dao.insert(mockAccount)
        dao.insert(mockAccount)
        dao.getAll()
                .test()
                .awaitDone(1, TimeUnit.SECONDS)
                .assertValue {
                    it.size == 4
                }
    }

}