package ru.nsu.currency.converter.data.datasource

import retrofit2.Response
import retrofit2.http.GET
import ru.nsu.currency.converter.data.dto.CBRResponse

interface CurrencyApi {
    @GET("daily_json.js")
    suspend fun getCurrencyRates(): Response<CBRResponse>
}