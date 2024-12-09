package ru.nsu.contact.application.domain.usecase

import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.domain.repository.ContactRepository
import javax.inject.Inject

class AddContactUseCase @Inject constructor(private val repository: ContactRepository) {
    suspend fun invoke(contact: Contact) {
        repository.insertContact(contact)
    }
}