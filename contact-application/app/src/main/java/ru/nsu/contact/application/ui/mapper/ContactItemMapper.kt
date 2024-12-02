package ru.nsu.contact.application.ui.mapper

import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.ui.item.Item
import javax.inject.Inject

class ContactItemMapper @Inject constructor() {

    fun contactToContactItem(contacts: List<Contact>): List<Item> {
        return contacts.map { contactToContactItem(it) }
    }

    fun contactItemToContact(contactItem: Item.ContactItem): Contact {
        return Contact(
            contactItem.id,
            contactItem.name,
            contactItem.phoneNumber,
            contactItem.photoUri
        )
    }

    private fun contactToContactItem(contact: Contact): Item {
        return Item.ContactItem(contact.id, contact.name, contact.phoneNumber, contact.photoUri)
    }
}