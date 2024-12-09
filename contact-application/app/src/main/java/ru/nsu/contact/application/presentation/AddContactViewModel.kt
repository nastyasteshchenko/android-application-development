package ru.nsu.contact.application.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.domain.usecase.AddContactUseCase
import ru.nsu.contact.application.domain.usecase.GetAllContactsUseCase
import javax.inject.Inject

class AddContactViewModel(
    private val getAllContactsUseCase: GetAllContactsUseCase,
    private val addContactUseCase: AddContactUseCase,
    private val contactLiveDataRepository: ContactLiveDataRepository
) : ViewModel() {

    fun addContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            addContactUseCase.invoke(contact)
            contactLiveDataRepository.updateContacts(getAllContactsUseCase.invoke())
        }
    }

    class ViewModelFactory @Inject constructor(
        private val getAllContactsUseCase: GetAllContactsUseCase,
        private val addContactUseCase: AddContactUseCase,
        private val contactLiveDataRepository: ContactLiveDataRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddContactViewModel(
                getAllContactsUseCase,
                addContactUseCase,
                contactLiveDataRepository
            ) as T
        }
    }
}