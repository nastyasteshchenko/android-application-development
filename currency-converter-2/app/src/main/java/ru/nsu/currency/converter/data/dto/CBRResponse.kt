package ru.nsu.currency.converter.data.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class CBRResponse @JsonCreator constructor(
    @JsonProperty("Date") val date: String,
    @JsonProperty("PreviousDate") val previousDate: String,
    @JsonProperty("PreviousURL") val previousUrl: String,
    @JsonProperty("Timestamp") val timestamp: String,
    @JsonProperty("Valute") val valute: HashMap<String, CurrencyDto>
)


