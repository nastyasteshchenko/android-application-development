package ru.nsu.currency.converter.di.component

import dagger.Component
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention
@Singleton
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