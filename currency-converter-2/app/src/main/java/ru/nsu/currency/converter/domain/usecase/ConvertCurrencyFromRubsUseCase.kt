package ru.nsu.currency.converter.domain.usecase

import ru.nsu.currency.converter.domain.model.Currency
import javax.inject.Inject

class ConvertCurrencyFromRubsUseCase @Inject constructor(){
    //TODO checking
    fun invoke(rubAmount: Double, currency: Currency): Double {
        return rubAmount / currency.value
    }
}