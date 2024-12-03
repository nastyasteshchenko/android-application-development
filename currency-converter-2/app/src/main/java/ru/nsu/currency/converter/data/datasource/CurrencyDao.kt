package ru.nsu.currency.converter.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.nsu.currency.converter.data.entity.CurrencyEntity

@Dao
interface CurrencyDao {

    @Query("Select * FROM currency ORDER BY currency.name ASC")
    suspend fun getCurrencies(): List<CurrencyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencies(currencies: List<CurrencyEntity>)
}