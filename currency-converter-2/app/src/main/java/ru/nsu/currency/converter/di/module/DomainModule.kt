package ru.nsu.currency.converter.di.module

import dagger.Module
import dagger.Provides
import ru.nsu.currency.converter.data.datasource.CurrencyApi
import ru.nsu.currency.converter.data.datasource.CurrencyDataBase
import ru.nsu.currency.converter.data.mapper.CurrencyEntityMapper
import ru.nsu.currency.converter.data.repository.CurrencyRepositoryImpl
import ru.nsu.currency.converter.di.component.DomainScope
import ru.nsu.currency.converter.domain.repository.CurrencyRepository

@Module
class DomainModule {

    @DomainScope
    @Provides
    fun provideCurrencyRepository(
        currencyApi: CurrencyApi,
        database: CurrencyDataBase,
        currencyEntityMapper: CurrencyEntityMapper
    ): CurrencyRepository = CurrencyRepositoryImpl(
        currencyApi,
        database.currencyDao(),
        currencyEntityMapper
    )
}