package ru.nsu.contact.application.di.module

import android.content.ContentResolver
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.data.datasource.ContactDataBase
import ru.nsu.contact.application.di.component.DataScope

@Module
class ProvidesDataModule {

    @DataScope
    @Provides
    fun provideContactDatabase(application: Application) = Room.databaseBuilder(
        application, ContactDataBase::class.java, ContactDataBase.DATABASE_NAME
    ).build()

    @Provides
    fun provideContentResolver(application: Application): ContentResolver {
        return application.contentResolver
    }
}