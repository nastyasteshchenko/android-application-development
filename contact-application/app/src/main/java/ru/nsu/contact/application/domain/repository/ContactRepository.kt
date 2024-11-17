package ru.nsu.contact.application.domain.repository

import ru.nsu.contact.application.domain.model.Contact

interface ContactRepository {
    suspend fun getContacts(): List<Contact>

    suspend fun insertContact(contact: Contact)

    suspend fun insertContacts(contacts: List<Contact>)

    suspend fun deleteContact(contact: Contact)
}