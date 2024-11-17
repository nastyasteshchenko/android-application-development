package ru.nsu.contact.application.data.mapper

import ru.nsu.contact.application.data.entity.ContactEntity
import ru.nsu.contact.application.domain.model.Contact

class ContactEntityMapper {
    fun toContactEntity(contact: Contact): ContactEntity {
        return ContactEntity(
            contact.id,
            contact.name,
            contact.phoneNumber,
            contact.photoUri
        )
    }

    fun toContact(contactEntity: ContactEntity): Contact {
        return Contact(
            contactEntity.id,
            contactEntity.name,
            contactEntity.phoneNumber,
            contactEntity.photoUri
        )
    }
}