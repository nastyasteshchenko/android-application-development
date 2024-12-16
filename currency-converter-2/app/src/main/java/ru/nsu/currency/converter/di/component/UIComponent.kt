package ru.nsu.currency.converter.di.component

import dagger.Component
import ru.nsu.currency.converter.domain.repository.CurrencyRepository
import ru.nsu.currency.converter.ui.fragment.CurrencyConverterFragment
import ru.nsu.currency.converter.ui.fragment.CurrencyListFragment
import javax.inject.Scope

@Scope
@Retention
annotation class UIScope

@UIScope
@Component(dependencies = [DomainComponent::class])
interface UIComponent {

    val currencyRepository: CurrencyRepository

    @Component.Factory
    interface Factory {
        fun create(domainComponent: DomainComponent): UIComponent
    }

    fun inject(fragment: CurrencyListFragment)
    fun inject(fragment: CurrencyConverterFragment)
}