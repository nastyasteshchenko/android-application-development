package ru.nsu.contact.application.data.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.contact.application.data.datasource.ContactDao
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.domain.repository.ContactRepository

class ContactRepositoryImpl(
    private val contactDao: ContactDao
) : ContactRepository {

    override fun getContacts(): Flow<List<Contact>> {
        return contactDao.getContacts()
    }

    override suspend fun getContactById(contactId: Int): Contact? {
        return contactDao.getContactById(contactId)
    }

    override suspend fun insertContact(contact: Contact) {
        return contactDao.insertContact(contact)
    }

    override suspend fun deleteContact(contact: Contact) {
        return contactDao.deleteContact(contact)
    }

}