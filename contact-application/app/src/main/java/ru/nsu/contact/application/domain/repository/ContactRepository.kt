package ru.nsu.contact.application.domain.repository

import ru.nsu.contact.application.domain.model.Contact

interface ContactRepository {
    suspend fun getContacts(): List<Contact>

    suspend fun getContactById(contactId: Long): Contact?

    suspend fun insertContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)
}