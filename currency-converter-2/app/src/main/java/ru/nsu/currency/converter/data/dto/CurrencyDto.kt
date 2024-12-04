package ru.nsu.currency.converter.data.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class CurrencyDto @JsonCreator constructor(
    @JsonProperty("Name") val name: String,
    @JsonProperty("Value") val value: Double,
    @JsonProperty("Previous") val previous: Double,
    @JsonProperty("CharCode") val charCode: String,
    @JsonProperty("Nominal") val nominal: Int,
    @JsonProperty("ID") val id: String,
    @JsonProperty("NumCode") val numCode: Int
)
