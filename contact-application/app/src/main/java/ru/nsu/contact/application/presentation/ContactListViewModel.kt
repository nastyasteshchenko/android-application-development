package ru.nsu.contact.application.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nsu.contact.application.domain.model.Banner
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.domain.usecase.GetAdvertisementBannerUseCase
import ru.nsu.contact.application.domain.usecase.GetAllContactsUseCase
import javax.inject.Inject

class ContactListViewModel(
    private val getAllContactsUseCase: GetAllContactsUseCase,
    private val getAdvertisementBannerUseCase: GetAdvertisementBannerUseCase,
    private val contactLiveDataRepository: ContactLiveDataRepository
) : ViewModel() {

    fun fetchContacts() {
        viewModelScope.launch(Dispatchers.IO) {
            contactLiveDataRepository.updateContacts(getAllContactsUseCase.invoke())
        }
    }

    fun observeLiveData(
        lifecycleOwner: LifecycleOwner,
        observer: Observer<List<Contact>>
    ) {
        contactLiveDataRepository.contacts.observe(lifecycleOwner, observer)
    }

    fun getBanner(): Banner {
        return getAdvertisementBannerUseCase.invoke()
    }

    class ViewModelFactory @Inject constructor(
        private val getAllContactsUseCase: GetAllContactsUseCase,
        private val getAdvertisementBannerUseCase: GetAdvertisementBannerUseCase,
        private val contactLiveDataRepository: ContactLiveDataRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ContactListViewModel(
                getAllContactsUseCase,
                getAdvertisementBannerUseCase,
                contactLiveDataRepository
            ) as T
        }
    }
}