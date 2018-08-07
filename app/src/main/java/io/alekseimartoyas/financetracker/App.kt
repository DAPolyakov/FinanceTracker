package io.alekseimartoyas.financetracker

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import io.alekseimartoyas.financetracker.data.local.Account
import io.alekseimartoyas.financetracker.data.local.AppDatabase
import io.alekseimartoyas.financetracker.di.DaggerServicesEntityComponent
import io.alekseimartoyas.financetracker.di.ServicesEntityComponent
import io.alekseimartoyas.financetracker.domain.Currency
import java.math.BigDecimal
import java.util.concurrent.Executors

class App : Application() {

    companion object {
        lateinit var graph: ServicesEntityComponent
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerServicesEntityComponent.create()
        initDb()
    }

    private fun initDb() {
        db = Room.databaseBuilder(this, AppDatabase::class.java, "finance_tracker")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        Executors.newSingleThreadExecutor().execute {
                            App.db.accountDao.insert(Account("Наличные", Currency.RUB, BigDecimal(300)))
                            App.db.accountDao.insert(Account("На карте", Currency.RUB, BigDecimal(50000)))
                            App.db.accountDao.insert(Account("В банке", Currency.USD, BigDecimal(100000)))
                        }
                    }
                })
                .build()
    }
}