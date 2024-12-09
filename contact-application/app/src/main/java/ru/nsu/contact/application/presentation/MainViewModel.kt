package ru.nsu.contact.application.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nsu.contact.application.domain.usecase.AddContactsUseCase
import ru.nsu.contact.application.domain.usecase.GetAllContactsUseCase
import ru.nsu.contact.application.domain.usecase.GetContactsFromContactBookUseCase
import javax.inject.Inject

class MainViewModel(
    private val getAllContactsUseCase: GetAllContactsUseCase,
    private val addContactsUseCase: AddContactsUseCase,
    private val getContactsFromContactBookUseCase: GetContactsFromContactBookUseCase,
    private val contactLiveDataRepository: ContactLiveDataRepository
) : ViewModel() {

    fun fetchContactsFromContactBook() {
        viewModelScope.launch(Dispatchers.IO) {
            val contacts = getContactsFromContactBookUseCase.invoke()
            addContactsUseCase.invoke(contacts)
            contactLiveDataRepository.updateContacts(getAllContactsUseCase.invoke())
        }
    }

    class ViewModelFactory @Inject constructor(
        private val getAllContactsUseCase: GetAllContactsUseCase,
        private val addContactsUseCase: AddContactsUseCase,
        private val getContactsFromContactBookUseCase: GetContactsFromContactBookUseCase,
        private val contactLiveDataRepository: ContactLiveDataRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(
                getAllContactsUseCase,
                addContactsUseCase,
                getContactsFromContactBookUseCase,
                contactLiveDataRepository
            ) as T
        }
    }
}