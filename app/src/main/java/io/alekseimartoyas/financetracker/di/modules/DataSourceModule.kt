package io.alekseimartoyas.financetracker.di.modules

import dagger.Module
import dagger.Provides
import io.alekseimartoyas.financetracker.App
import io.alekseimartoyas.financetracker.data.services.DataSource
import javax.inject.Singleton

@Module
object DataSourceModule {

    @JvmStatic
    @Singleton
    @Provides
    fun dataSource(): DataSource = DataSource(App.db)


//    @Provides
//    fun provideDatabase(context: Context) =
//            Room.databaseBuilder(context, AppDatabase::class.java, "finance_tracker").build()
}