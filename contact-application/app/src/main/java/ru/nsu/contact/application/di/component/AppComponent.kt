package ru.nsu.contact.application.di.component

import dagger.Component
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.di.module.BindsDataModule
import ru.nsu.contact.application.di.module.ProvidesDataModule
import ru.nsu.contact.application.di.module.DomainModule
import ru.nsu.contact.application.ui.MainActivity
import ru.nsu.contact.application.ui.fragment.AddContactFragment
import ru.nsu.contact.application.ui.fragment.ContactListFragment
import ru.nsu.contact.application.ui.fragment.EditContactFragment
import ru.nsu.contact.application.ui.fragment.ShowContactFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [ProvidesDataModule::class, BindsDataModule::class, DomainModule::class])
interface AppComponent {

    fun inject(fragment: EditContactFragment)
    fun inject(fragment: ShowContactFragment)
    fun inject(fragment: ContactListFragment)
    fun inject(fragment: AddContactFragment)
    fun inject(activity: MainActivity)
    fun inject(application: Application)
}