package ru.nsu.currency.converter

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.nsu.currency.converter.model.Currency
import ru.nsu.currency.converter.data.dto.CBRResponse
import ru.nsu.currency.converter.data.dto.mapToDomainModel
import java.io.IOException
import java.util.Collections

class CurrencyDataTaker {

    companion object {
        private const val REQUEST_URL = "https://www.cbr-xml-daily.ru/daily_json.js"
    }

    fun getCurrencyList(): List<Currency> {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(REQUEST_URL)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val json = response.body?.string()
            if (json != null) {
                val currencyResponse = Gson().fromJson(json, CBRResponse::class.java)

                return currencyResponse.valute.values.map { currency -> currency.mapToDomainModel() }
                    .toList()
            }
            return Collections.emptyList()
        }
    }
}