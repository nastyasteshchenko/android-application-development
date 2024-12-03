package ru.nsu.currency.converter.data.mapper

import ru.nsu.currency.converter.data.dto.CurrencyDto
import ru.nsu.currency.converter.data.entity.CurrencyEntity
import ru.nsu.currency.converter.domain.model.Currency
import javax.inject.Inject

class CurrencyEntityMapper @Inject constructor() {

    fun currencyEntityToCurrency(currencyEntity: List<CurrencyEntity>): List<Currency> {
        return currencyEntity.map { currencyEntityToCurrency(it) }
    }

    fun currencyDtoToCurrencyEntity(currenciesDtos: List<CurrencyDto>): List<CurrencyEntity> {
        return currenciesDtos.map { currencyDtoToCurrencyEntity(it) }
    }

    private fun currencyEntityToCurrency(currencyEntity: CurrencyEntity): Currency {
        return Currency(
            currencyEntity.id,
            currencyEntity.name, currencyEntity.value,
            currencyEntity.prevValue, currencyEntity.charCode
        )
    }

    private fun currencyDtoToCurrencyEntity(currencyDto: CurrencyDto): CurrencyEntity {
        return CurrencyEntity(
            currencyDto.id,
            currencyDto.name, currencyDto.value,
            currencyDto.previous, currencyDto.charCode
        )
    }
}