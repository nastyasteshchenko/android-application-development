package ru.nsu.currency.converter.data.dto

import com.google.gson.annotations.SerializedName

data class CBRResponse(
    @SerializedName("Date") val date: String,
    @SerializedName("PreviousDate") val prevDate: String,
    @SerializedName("PreviousURL") val prevUrl: String,
    @SerializedName("Timestamp") val timestamp: String,
    @SerializedName("Valute") val valute: HashMap<String, ValuteResponse>
)


