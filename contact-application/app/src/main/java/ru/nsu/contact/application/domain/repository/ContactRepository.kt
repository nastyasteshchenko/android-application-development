package ru.nsu.contact.application.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.nsu.contact.application.domain.model.Contact

interface ContactRepository {
    fun getContacts(): Flow<List<Contact>>

    suspend fun getContactById(contactId: Int): Contact?

    suspend fun insertContact(contact: Contact)

    suspend fun deleteContact(contact: Contact)
}