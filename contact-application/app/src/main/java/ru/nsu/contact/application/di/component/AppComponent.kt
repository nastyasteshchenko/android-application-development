package ru.nsu.contact.application.di.component

import dagger.Component
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.di.module.DataModule
import ru.nsu.contact.application.di.module.DomainModule
import ru.nsu.contact.application.ui.AddContactActivity
import ru.nsu.contact.application.ui.MainActivity
import ru.nsu.contact.application.ui.EditContactActivity
import ru.nsu.contact.application.ui.ShowContactInfoActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface AppComponent {

    fun inject(activity: ShowContactInfoActivity)
    fun inject(activity: EditContactActivity)
    fun inject(activity: AddContactActivity)
    fun inject(activity: MainActivity)
    fun inject(application: Application)
}