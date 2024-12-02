package ru.nsu.currency.converter

import ru.nsu.currency.converter.model.Currency

interface OnCurrencyClickListener {
        fun onConvertCurrency(currency: Currency)
    }