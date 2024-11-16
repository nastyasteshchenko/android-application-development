package ru.nsu.contact.application.di.module

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.nsu.contact.application.data.datasource.ContactDataBase
import javax.inject.Singleton

@Module
class DataModule(private val app: Application) {
    @Singleton
    @Provides
    fun provideNoteDatabase() = Room.databaseBuilder(
        app, ContactDataBase::class.java, ContactDataBase.DATABASE_NAME
    ).build()
}