package ru.nsu.currency.converter.domain.repository

import ru.nsu.currency.converter.domain.model.Currency

interface CurrencyRepository {
    suspend fun getCurrencies(): List<Currency>
    suspend fun refreshCurrencies()
}