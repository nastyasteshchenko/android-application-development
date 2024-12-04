package ru.nsu.currency.converter.di.module

import android.app.Application
import android.content.ContentResolver
import androidx.room.Room
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import ru.nsu.currency.converter.data.datasource.CurrencyApi
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

    @DataScope
    @Provides
    fun provideObjectMapper(): ObjectMapper {
        return ObjectMapper().apply {
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true)
        }
    }

    @DataScope
    @Provides
    fun provideRetrofit(objectMapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.cbr-xml-daily.ru/")
            .addConverterFactory(JacksonConverterFactory.create(objectMapper))
            .build()
    }

    @Provides
    fun provideCurrencyApi(retrofit: Retrofit): CurrencyApi {
        return retrofit.create(CurrencyApi::class.java)
    }
}