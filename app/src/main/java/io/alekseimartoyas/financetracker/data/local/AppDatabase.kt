package io.alekseimartoyas.financetracker.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase


@Database(entities = [Account::class, FinanceTransaction::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract val accountDao: AccountDao
    abstract val financeTransactionDao: FinanceTransactionDao
}