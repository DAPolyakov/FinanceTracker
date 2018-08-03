package io.alekseimartoyas.financetracker

import android.app.Application
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import io.alekseimartoyas.financetracker.data.local.AppDatabase
import io.alekseimartoyas.financetracker.di.DaggerServicesEntityComponent
import io.alekseimartoyas.financetracker.di.ServicesEntityComponent

class App : Application() {

    companion object {
        lateinit var graph: ServicesEntityComponent
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()

        graph = DaggerServicesEntityComponent.create()
        db = Room.databaseBuilder(this, AppDatabase::class.java, "finance_tracker").build()
    }
}