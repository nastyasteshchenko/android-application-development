package ru.nsu.contact.application.di.module

import dagger.Module
import dagger.Provides
import ru.nsu.contact.application.data.datasource.ContactDataBase
import ru.nsu.contact.application.data.datasource.ContactsFromBookDataSource
import ru.nsu.contact.application.data.mapper.ContactEntityMapper
import ru.nsu.contact.application.data.repository.ContactRepositoryImpl
import ru.nsu.contact.application.di.component.DomainScope
import ru.nsu.contact.application.domain.repository.ContactRepository

@Module
class ProvidesDomainModule {

    @DomainScope
    @Provides
    fun provideNoteRepository(
        database: ContactDataBase,
        contactsFromBookDataSource: ContactsFromBookDataSource
    ): ContactRepository = ContactRepositoryImpl(
        database.contactDao(), ContactEntityMapper(), contactsFromBookDataSource
    )
}
