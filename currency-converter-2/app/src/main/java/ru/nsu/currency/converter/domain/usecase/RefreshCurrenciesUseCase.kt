package ru.nsu.currency.converter.domain.usecase

import ru.nsu.currency.converter.domain.repository.CurrencyRepository
import javax.inject.Inject

class RefreshCurrenciesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository
) {

    suspend fun invoke() {
        currencyRepository.refreshCurrencies()
    }
}