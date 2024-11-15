package ru.nsu.contact.application.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.nsu.contact.application.data.datasource.ContactDao
import ru.nsu.contact.application.data.mapper.ContactEntityMapper
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.domain.repository.ContactRepository

class ContactRepositoryImpl(
    private val contactDao: ContactDao,
    private val contactEntityMapper: ContactEntityMapper
) : ContactRepository {

    override fun getContacts(): Flow<List<Contact>> {
        return contactDao.getContacts().map { list ->
            list.map { element ->
                contactEntityMapper.toContact(element)
            }
        }
    }

    override suspend fun getContactById(contactId: Int): Contact? {
        val contactEntity = contactDao.getContactById(contactId) ?: return null
        return contactEntityMapper.toContact(contactEntity)
    }

    override suspend fun insertContact(contact: Contact) {
        return contactDao.insertContact(contactEntityMapper.toContactEntity(contact))
    }

    override suspend fun deleteContact(contact: Contact) {
        return contactDao.deleteContact(contactEntityMapper.toContactEntity(contact))
    }

}