package ru.nsu.currency.converter.domain.model

data class Currency(
    val id: String,
    val name: String,
    val value: Double,
    val prevValue: Double,
    val charCode: String
)
