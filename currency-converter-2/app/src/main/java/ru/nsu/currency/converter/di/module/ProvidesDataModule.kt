package ru.nsu.currency.converter.di.module

import android.app.Application
import android.content.ContentResolver
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.nsu.currency.converter.data.datasource.CurrencyDataBase
import ru.nsu.currency.converter.di.component.DataScope

@Module
class ProvidesDataModule {

    @DataScope
    @Provides
    fun provideCurrencyDatabase(application: Application) = Room.databaseBuilder(
        application, CurrencyDataBase::class.java, CurrencyDataBase.DATABASE_NAME
    ).build()

    @Provides
    fun provideContentResolver(application: Application): ContentResolver {
        return application.contentResolver
    }
}