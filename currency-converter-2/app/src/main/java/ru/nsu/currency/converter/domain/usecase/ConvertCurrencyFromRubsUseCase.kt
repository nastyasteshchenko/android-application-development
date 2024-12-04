package ru.nsu.currency.converter.domain.usecase

import ru.nsu.currency.converter.domain.exception.WrongRubAmountException
import ru.nsu.currency.converter.domain.model.Currency
import javax.inject.Inject

class ConvertCurrencyFromRubsUseCase @Inject constructor() {

    fun invoke(rubAmount: Double?, currency: Currency): Double {
        if (rubAmount == null) {
            throw WrongRubAmountException("Не указано количество рублей")
        }
        if (rubAmount < 0) {
            throw WrongRubAmountException("Количество рублей не может быть отрицательным")
        }
        return rubAmount / currency.value
    }
}