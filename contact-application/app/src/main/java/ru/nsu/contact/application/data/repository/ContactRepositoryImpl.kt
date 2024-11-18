package ru.nsu.contact.application.data.repository

import ru.nsu.contact.application.data.datasource.ContactDao
import ru.nsu.contact.application.data.datasource.ContactsFromBookDataSource
import ru.nsu.contact.application.data.mapper.ContactEntityMapper
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.domain.repository.ContactRepository
import javax.inject.Inject

class ContactRepositoryImpl
@Inject constructor(
    private val contactDao: ContactDao,
    private val contactEntityMapper: ContactEntityMapper,
    private val contactsFromBookDataSource: ContactsFromBookDataSource
) : ContactRepository {

    override suspend fun getContacts(): List<Contact> {
        return contactDao.getContacts().map { element ->
            contactEntityMapper.toContact(element)
        }
    }

    override suspend fun insertContact(contact: Contact) {
        return contactDao.insertContact(contactEntityMapper.toContactEntity(contact))
    }

    override suspend fun insertContacts(contacts: List<Contact>) {
        val contactEntities = contacts.map { contactEntityMapper.toContactEntity(it) }.toList()
        return contactDao.insertContacts(contactEntities)
    }

    override suspend fun deleteContact(contact: Contact) {
        return contactDao.deleteContact(contactEntityMapper.toContactEntity(contact))
    }

    override suspend fun getContactsFromContactBook(): List<Contact> {
        return contactsFromBookDataSource.getContacts()
    }

}