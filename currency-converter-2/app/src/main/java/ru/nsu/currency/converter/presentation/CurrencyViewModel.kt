package ru.nsu.currency.converter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nsu.currency.converter.domain.model.Currency
import ru.nsu.currency.converter.domain.usecase.ConvertCurrencyFromRubsUseCase
import ru.nsu.currency.converter.domain.usecase.GetCurrenciesUseCase
import ru.nsu.currency.converter.domain.usecase.RefreshCurrenciesUseCase
import javax.inject.Inject

class CurrencyViewModel(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val refreshCurrenciesUseCase: RefreshCurrenciesUseCase,
    private val convertCurrencyFromRubsUseCase: ConvertCurrencyFromRubsUseCase
) : ViewModel() {

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies: LiveData<List<Currency>> get() = _currencies

    fun convertCurrencyFromRubs(rubAmount: Double, currency: Currency): Double {
        return convertCurrencyFromRubsUseCase.invoke(rubAmount, currency)
    }

    fun refreshCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            refreshCurrenciesUseCase.invoke()
        }
    }

    fun fetchCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            _currencies.postValue(getCurrenciesUseCase.invoke())
        }
    }

    fun getCurrencies(): List<Currency> {
        return currencies.value!!
    }

    class ViewModelFactory @Inject constructor(
        private val getCurrenciesUseCase: GetCurrenciesUseCase,
        private val refreshCurrenciesUseCase: RefreshCurrenciesUseCase,
        private val convertCurrencyFromRubsUseCase: ConvertCurrencyFromRubsUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CurrencyViewModel(
                getCurrenciesUseCase,
                refreshCurrenciesUseCase,
                convertCurrencyFromRubsUseCase
            ) as T
        }
    }
}