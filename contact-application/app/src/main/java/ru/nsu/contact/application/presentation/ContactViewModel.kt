package ru.nsu.contact.application.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.domain.usecase.AddContactUseCase
import ru.nsu.contact.application.domain.usecase.AddContactsUseCase
import ru.nsu.contact.application.domain.usecase.DeleteContactUseCase
import ru.nsu.contact.application.domain.usecase.GetAllContactsUseCase
import ru.nsu.contact.application.domain.usecase.GetContactsFromContactBookUseCase
import ru.nsu.contact.application.domain.usecase.UpdateContactUseCase
import javax.inject.Inject

class ContactViewModel(
    private val deleteContactUseCase: DeleteContactUseCase,
    private val updateContactUseCase: UpdateContactUseCase,
    private val addContactUseCase: AddContactUseCase,
    private val addContactsUseCase: AddContactsUseCase,
    private val getAllContactsUseCase: GetAllContactsUseCase,
    private val getContactsFromContactBookUseCase: GetContactsFromContactBookUseCase
) : ViewModel() {

    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    fun fetchContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            _contacts.postValue(getAllContactsUseCase.invoke())
        }
    }

    fun fetchContactsFromContactBook() {
        viewModelScope.launch(Dispatchers.IO) {
            val contacts = getContactsFromContactBookUseCase.invoke()
            addContactsUseCase.invoke(contacts)
            _contacts.value = getAllContactsUseCase.invoke()
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            addContactUseCase.invoke(contact)
            fetchContacts()
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteContactUseCase.invoke(contact)
            fetchContacts()
        }
    }

    fun updateContact(contact: Contact) {
        viewModelScope.launch(Dispatchers.IO) {
            updateContactUseCase.invoke(contact)
            fetchContacts()
        }
    }

    class ViewModelFactory @Inject constructor(
        private val deleteContactUseCase: DeleteContactUseCase,
        private val updateContactUseCase: UpdateContactUseCase,
        private val addContactUseCase: AddContactUseCase,
        private val addContactsUseCase: AddContactsUseCase,
        private val getAllContactsUseCase: GetAllContactsUseCase,
        private val getContactsFromContactBookUseCase: GetContactsFromContactBookUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ContactViewModel(
                deleteContactUseCase,
                updateContactUseCase,
                addContactUseCase,
                addContactsUseCase,
                getAllContactsUseCase,
                getContactsFromContactBookUseCase
            ) as T
        }
    }
}