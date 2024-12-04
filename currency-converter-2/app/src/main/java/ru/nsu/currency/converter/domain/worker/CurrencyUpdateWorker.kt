package ru.nsu.currency.converter.domain.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.nsu.currency.converter.domain.repository.CurrencyRepository

class CurrencyUpdateWorker(
    private val currencyRepository: CurrencyRepository,
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            currencyRepository.refreshCurrencies()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}