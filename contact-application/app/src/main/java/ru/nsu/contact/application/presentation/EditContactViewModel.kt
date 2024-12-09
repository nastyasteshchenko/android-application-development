package ru.nsu.contact.application.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.domain.usecase.DeleteContactUseCase
import ru.nsu.contact.application.domain.usecase.GetAllContactsUseCase
import ru.nsu.contact.application.domain.usecase.UpdateContactUseCase
import javax.inject.Inject

class EditContactViewModel(
    private val getAllContactsUseCase: GetAllContactsUseCase,
    private val deleteContactUseCase: DeleteContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val contactLiveDataRepository: ContactLiveDataRepository
) : ViewModel() {

    fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteContactUseCase.invoke(contact)
            contactLiveDataRepository.updateContacts(getAllContactsUseCase.invoke())
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            updateContactUseCase.invoke(contact)
            contactLiveDataRepository.updateContacts(getAllContactsUseCase.invoke())
        }
    }

    class ViewModelFactory @Inject constructor(
        private val getAllContactsUseCase: GetAllContactsUseCase,
        private val deleteContactUseCase: DeleteContactUseCase,
        private val updateContactUseCase: UpdateContactUseCase,
        private val contactLiveDataRepository: ContactLiveDataRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditContactViewModel(
                getAllContactsUseCase,
                deleteContactUseCase,
                updateContactUseCase,
                contactLiveDataRepository
            ) as T
        }
    }
}