package ru.nsu.currency.converter.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import ru.nsu.currency.converter.data.datasource.CurrencyApi
import ru.nsu.currency.converter.data.datasource.CurrencyDataBase
import ru.nsu.currency.converter.di.module.ProvidesDataModule
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention
@Singleton
annotation class DataScope

@DataScope
@Component(modules = [ProvidesDataModule::class])
interface DataComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): DataComponent
    }

    fun provideCurrencyDatabase(): CurrencyDataBase
    fun provideCurrencyApi(): CurrencyApi
}