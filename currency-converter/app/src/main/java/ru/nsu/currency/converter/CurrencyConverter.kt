package ru.nsu.currency.converter

import ru.nsu.currency.converter.model.Currency

class CurrencyConverter {

    fun convertFromRubs(rubAmount: Double, currency: Currency): Double {
        return rubAmount / currency.value
    }

}