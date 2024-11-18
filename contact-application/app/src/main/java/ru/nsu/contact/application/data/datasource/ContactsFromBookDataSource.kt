package ru.nsu.contact.application.data.datasource

import ru.nsu.contact.application.domain.model.Contact

interface ContactsFromBookDataSource {
    fun getContacts(): List<Contact>
}