package ru.nsu.currency.converter.domain.model

import java.io.Serializable

data class Currency(
    val id: String,
    val name: String,
    val value: Double,
    val prevValue: Double,
    val charCode: String
) : Serializable
