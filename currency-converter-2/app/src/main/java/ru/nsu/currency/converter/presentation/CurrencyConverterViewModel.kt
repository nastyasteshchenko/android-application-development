package ru.nsu.currency.converter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.nsu.currency.converter.domain.model.Currency
import ru.nsu.currency.converter.domain.usecase.ConvertCurrencyFromRubsUseCase
import ru.nsu.currency.converter.domain.usecase.GetCurrenciesUseCase
import javax.inject.Inject

class CurrencyConverterViewModel(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val convertCurrencyFromRubsUseCase: ConvertCurrencyFromRubsUseCase
) : ViewModel() {

    fun convertCurrencyFromRubs(rubAmount: Double?, currency: Currency): Double {
        return convertCurrencyFromRubsUseCase.invoke(rubAmount, currency)
    }

    suspend fun getCurrencies(): List<Currency> {
        return getCurrenciesUseCase.invoke()
    }

    class ViewModelFactory @Inject constructor(
        private val getCurrenciesUseCase: GetCurrenciesUseCase,
        private val convertCurrencyFromRubsUseCase: ConvertCurrencyFromRubsUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CurrencyConverterViewModel(
                getCurrenciesUseCase,
                convertCurrencyFromRubsUseCase
            ) as T
        }
    }
}