package ru.nsu.currency.converter.di.module

import dagger.Module
import dagger.Provides
import ru.nsu.currency.converter.data.datasource.CurrencyDataBase
import ru.nsu.currency.converter.di.component.DomainScope

@Module
class DomainModule {

//    @DomainScope
//    @Provides
//    fun provideCurrencyRepository(
//        database: CurrencyDataBase,
//        contactsFromBookDataSource: ContactsFromBookDataSource
//    ): ContactRepository = ContactRepositoryImpl(
//        database.contactDao(), ContactEntityMapper(), contactsFromBookDataSource
//    )
}