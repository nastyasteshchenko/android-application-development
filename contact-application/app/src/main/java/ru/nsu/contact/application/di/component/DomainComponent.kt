package ru.nsu.contact.application.di.component

import dagger.Component
import ru.nsu.contact.application.di.module.DomainModule
import ru.nsu.contact.application.domain.repository.ContactRepository
import javax.inject.Scope

@Scope
@Retention
annotation class DomainScope

@DomainScope
@Component(modules = [DomainModule::class], dependencies = [DataComponent::class])
interface DomainComponent {

    @Component.Factory
    interface Factory {
        fun create(dataComponent: DataComponent): DomainComponent
    }

    fun provideContactRepository(): ContactRepository
}