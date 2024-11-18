package ru.nsu.contact.application.di.component

import dagger.Component
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.di.module.BindsDataModule
import ru.nsu.contact.application.di.module.ProvidesDataModule
import ru.nsu.contact.application.di.module.DomainModule
import ru.nsu.contact.application.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [ProvidesDataModule::class, BindsDataModule::class, DomainModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
    fun inject(application: Application)
}