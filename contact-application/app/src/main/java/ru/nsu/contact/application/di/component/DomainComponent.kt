package ru.nsu.contact.application.di.component

import dagger.Component
import ru.nsu.contact.application.di.module.BindsDomainModule
import ru.nsu.contact.application.di.module.ProvidesDomainModule
import ru.nsu.contact.application.domain.repository.AdvertisementBannerRepository
import ru.nsu.contact.application.domain.repository.ContactRepository
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention
@Singleton
annotation class DomainScope

@DomainScope
@Component(
    modules = [ProvidesDomainModule::class, BindsDomainModule::class],
    dependencies = [DataComponent::class]
)
interface DomainComponent {

    @Component.Factory
    interface Factory {
        fun create(dataComponent: DataComponent): DomainComponent
    }

    fun provideContactRepository(): ContactRepository
    fun provideAdvertisementBannerRepository(): AdvertisementBannerRepository
}