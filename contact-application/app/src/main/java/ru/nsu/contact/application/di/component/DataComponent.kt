package ru.nsu.contact.application.di.component

import dagger.BindsInstance
import dagger.Component
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.data.datasource.AdvertisementBannerDataSource
import ru.nsu.contact.application.data.datasource.ContactDataBase
import ru.nsu.contact.application.data.datasource.ContactsFromBookDataSource
import ru.nsu.contact.application.di.module.BindsDataModule
import ru.nsu.contact.application.di.module.ProvidesDataModule
import javax.inject.Scope
import javax.inject.Singleton

@Scope
@Retention
@Singleton
annotation class DataScope

@DataScope
@Component(modules = [ProvidesDataModule::class, BindsDataModule::class])
interface DataComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): DataComponent
    }

    fun provideContactDatabase(): ContactDataBase
    fun provideContactFromBookDataSource(): ContactsFromBookDataSource
    fun provideAdvertisementBannerDataSource(): AdvertisementBannerDataSource
}