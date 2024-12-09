package ru.nsu.contact.application.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.nsu.contact.application.di.component.UIScope
import ru.nsu.contact.application.domain.model.Contact
import javax.inject.Inject

@UIScope
class ContactLiveDataRepository @Inject constructor() {
    private val _contacts = MutableLiveData<List<Contact>>()
    val contacts: LiveData<List<Contact>> get() = _contacts

    fun updateContacts(newContacts: List<Contact>) {
        _contacts.postValue(newContacts)
    }
}