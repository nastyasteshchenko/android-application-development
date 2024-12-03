package ru.nsu.currency.converter.domain.usecase

import ru.nsu.currency.converter.domain.model.Currency
import ru.nsu.currency.converter.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetCurrenciesUseCase @Inject constructor(private val currencyRepository: CurrencyRepository) {

    suspend fun invoke(): List<Currency> {
        return currencyRepository.getCurrencies()
    }
}