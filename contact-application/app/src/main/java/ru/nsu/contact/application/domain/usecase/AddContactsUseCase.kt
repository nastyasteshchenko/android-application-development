package ru.nsu.contact.application.domain.usecase

import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.domain.repository.ContactRepository
import javax.inject.Inject

class AddContactsUseCase @Inject constructor(private val repository: ContactRepository) {
    suspend fun invoke(contacts: List<Contact>) {
        repository.insertContacts(contacts)
    }
}