package io.alekseimartoyas.financetracker.di

import dagger.Component
import io.alekseimartoyas.financetracker.data.services.DataSource
import io.alekseimartoyas.financetracker.data.services.ExchRateProvider
import io.alekseimartoyas.financetracker.di.modules.DataSourceModule
import io.alekseimartoyas.financetracker.di.modules.ExchRateProviderModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DataSourceModule::class,
    ExchRateProviderModule::class]
)
interface ServicesEntityComponent {
    fun getDataSource(): DataSource
    fun getExchRateProvider(): ExchRateProvider
}