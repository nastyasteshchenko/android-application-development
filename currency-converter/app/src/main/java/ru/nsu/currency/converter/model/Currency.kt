package ru.nsu.currency.converter.model

data class Currency(
    val name: String,
    val value: Double,
    val prevValue: Double,
    val charCode: String
)