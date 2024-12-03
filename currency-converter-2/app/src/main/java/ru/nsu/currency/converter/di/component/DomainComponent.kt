package ru.nsu.currency.converter.di.component

import dagger.Component
import ru.nsu.currency.converter.di.module.DomainModule
import ru.nsu.currency.converter.domain.repository.CurrencyRepository
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

    fun provideCurrencyRepository(): CurrencyRepository
}