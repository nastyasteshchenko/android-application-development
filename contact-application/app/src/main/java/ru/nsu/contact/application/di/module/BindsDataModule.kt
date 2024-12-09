package ru.nsu.contact.application.di.module

import dagger.Binds
import dagger.Module
import ru.nsu.contact.application.data.datasource.ContactsFromBookDataSource
import ru.nsu.contact.application.data.datasource.ContactsFromBookDataSourceImpl

@Module
interface BindsDataModule {
    @Binds
    fun bindContactFromBookDataSource(
        contactsFromBookDataSourceImpl: ContactsFromBookDataSourceImpl
    ): ContactsFromBookDataSource
}