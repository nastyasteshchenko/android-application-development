package ru.nsu.contact.application.di.component

import dagger.Component
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.di.module.DataModule
import ru.nsu.contact.application.di.module.DomainModule
import ru.nsu.contact.application.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(application: Application)
}