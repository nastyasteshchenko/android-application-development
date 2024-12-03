package ru.nsu.contact.application.di.module

import dagger.Binds
import dagger.Module
import ru.nsu.contact.application.data.repository.AdvertisementBannerRepositoryImpl
import ru.nsu.contact.application.di.component.DomainScope
import ru.nsu.contact.application.domain.repository.AdvertisementBannerRepository

@Module
interface BindsDomainModule {

    @DomainScope
    @Binds
    fun bindAdvertisementBannerRepository(
        advertisementBannerRepositoryImpl: AdvertisementBannerRepositoryImpl
    ): AdvertisementBannerRepository
}