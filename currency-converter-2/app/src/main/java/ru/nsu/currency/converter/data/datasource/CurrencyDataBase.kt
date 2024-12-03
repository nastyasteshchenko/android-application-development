package ru.nsu.currency.converter.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.nsu.currency.converter.data.entity.CurrencyEntity

@Database(entities = [CurrencyEntity::class], version = 1)
abstract class CurrencyDataBase : RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao

    companion object {
        const val DATABASE_NAME = "currency_db"
    }
}