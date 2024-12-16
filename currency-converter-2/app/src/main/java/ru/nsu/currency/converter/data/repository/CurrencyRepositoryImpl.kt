package ru.nsu.currency.converter.data.repository

import ru.nsu.currency.converter.data.datasource.CurrencyApi
import ru.nsu.currency.converter.data.datasource.CurrencyDao
import ru.nsu.currency.converter.data.mapper.CurrencyEntityMapper
import ru.nsu.currency.converter.domain.model.Currency
import ru.nsu.currency.converter.domain.repository.CurrencyRepository
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyApi: CurrencyApi,
    private val currencyDao: CurrencyDao,
    private val currencyEntityMapper: CurrencyEntityMapper
) : CurrencyRepository {

    override suspend fun getCurrencies(): List<Currency> {
        return currencyEntityMapper.currencyEntityToCurrency(currencyDao.getCurrencies())
    }

    override suspend fun refreshCurrencies() {
        val response = currencyApi.getCurrencies()
        if (response.isSuccessful) {
            response.body()?.valute?.values?.let { dtoList ->
                val entities = currencyEntityMapper.currencyDtoToCurrencyEntity(dtoList.toList())
                currencyDao.insertCurrencies(entities)
            }
        }
    }
}