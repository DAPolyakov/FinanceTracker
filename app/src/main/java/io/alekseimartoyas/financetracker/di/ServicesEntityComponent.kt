package io.alekseimartoyas.financetracker.di

import dagger.Component
import io.alekseimartoyas.financetracker.data.services.DataSource
import io.alekseimartoyas.financetracker.data.services.ExchRateProvider
import io.alekseimartoyas.financetracker.data.services.SettingsSevice
import io.alekseimartoyas.financetracker.di.modules.DataSourceModule
import io.alekseimartoyas.financetracker.di.modules.ExchRateProviderModule
import io.alekseimartoyas.financetracker.di.modules.SettingsModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    DataSourceModule::class,
    SettingsModule::class,
    ExchRateProviderModule::class]
)
interface ServicesEntityComponent {
    fun getDataSource(): DataSource
    fun getSettingsService(): SettingsSevice
    fun getExchRateProvider(): ExchRateProvider
}