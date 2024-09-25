package ru.nsu.currency.converter.response

import com.google.gson.annotations.SerializedName
import ru.nsu.currency.converter.model.Currency

data class ValuteResponse(
    @SerializedName("Name") val name: String,
    @SerializedName("Value") val value: Double,
    @SerializedName("Previous") val prevValue: Double,
    @SerializedName("CharCode") val charCode: String,
    @SerializedName("Nominal") val nominal: Int,
    @SerializedName("ID") val id : String,
    @SerializedName("NumCode") val numCode : Int
)

fun ValuteResponse.mapToDomainModel() = Currency(name, value, prevValue, charCode)
