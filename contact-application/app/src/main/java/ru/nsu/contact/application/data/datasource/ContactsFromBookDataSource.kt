package ru.nsu.contact.application.data.datasource

import ru.nsu.contact.application.data.entity.ContactEntity

interface ContactsFromBookDataSource {
    fun getContacts(): List<ContactEntity>
}