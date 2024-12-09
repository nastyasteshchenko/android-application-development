package ru.nsu.currency.converter.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.nsu.currency.converter.domain.model.Currency
import ru.nsu.currency.converter.domain.usecase.GetCurrenciesUseCase
import ru.nsu.currency.converter.domain.usecase.RefreshCurrenciesUseCase
import javax.inject.Inject

class CurrencyListViewModel(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val refreshCurrenciesUseCase: RefreshCurrenciesUseCase
) : ViewModel() {

    private val _currencies = MutableLiveData<List<Currency>>()
    val currencies: LiveData<List<Currency>> get() = _currencies

    fun refreshCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            refreshCurrenciesUseCase.invoke()
            fetchCurrencies()
        }
    }

    fun fetchCurrencies() {
        viewModelScope.launch(Dispatchers.IO) {
            _currencies.postValue(getCurrenciesUseCase.invoke())
        }
    }

    class ViewModelFactory @Inject constructor(
        private val getCurrenciesUseCase: GetCurrenciesUseCase,
        private val refreshCurrenciesUseCase: RefreshCurrenciesUseCase
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CurrencyListViewModel(
                getCurrenciesUseCase,
                refreshCurrenciesUseCase
            ) as T
        }
    }
}