package io.alekseimartoyas.financetracker.di.modules

import dagger.Module
import dagger.Provides
import io.alekseimartoyas.financetracker.data.local.AppDatabase
import io.alekseimartoyas.financetracker.data.services.DataSource

@Module
class DataSourceModule {

    @Provides
    fun dataSource(): DataSource = DataSource()


//    @Provides
//    fun provideDatabase(context: Context) =
//            Room.databaseBuilder(context, AppDatabase::class.java, "finance_tracker").build()
}