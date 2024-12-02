package ru.nsu.contact.application.di.component

import dagger.Component
import ru.nsu.contact.application.domain.repository.AdvertisementBannerRepository
import ru.nsu.contact.application.domain.repository.ContactRepository
import ru.nsu.contact.application.ui.MainActivity
import ru.nsu.contact.application.ui.fragment.AddContactFragment
import ru.nsu.contact.application.ui.fragment.ContactListFragment
import ru.nsu.contact.application.ui.fragment.EditContactFragment
import ru.nsu.contact.application.ui.fragment.ShowContactFragment
import javax.inject.Scope

@Scope
@Retention
annotation class UIScope

@UIScope
@Component(dependencies = [DomainComponent::class])
interface UIComponent {

    val contactRepository: ContactRepository
    val advertisementBannerRepository: AdvertisementBannerRepository

    @Component.Factory
    interface Factory {
        fun create(domainComponent: DomainComponent): UIComponent
    }

    fun inject(fragment: EditContactFragment)
    fun inject(fragment: ShowContactFragment)
    fun inject(fragment: ContactListFragment)
    fun inject(fragment: AddContactFragment)
    fun inject(activity: MainActivity)
}