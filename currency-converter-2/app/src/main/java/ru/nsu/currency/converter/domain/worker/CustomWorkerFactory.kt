package ru.nsu.currency.converter.domain.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.nsu.currency.converter.domain.repository.CurrencyRepository
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(
    private val currencyRepository: CurrencyRepository
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            CurrencyUpdateWorker::class.java.name -> {
                CurrencyUpdateWorker(currencyRepository, appContext, workerParameters)
            }

            else -> null
        }
    }
}