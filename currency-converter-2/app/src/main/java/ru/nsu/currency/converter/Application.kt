package ru.nsu.currency.converter

import android.app.Application
import androidx.work.Configuration
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ru.nsu.currency.converter.di.component.DaggerDataComponent
import ru.nsu.currency.converter.di.component.DaggerDomainComponent
import ru.nsu.currency.converter.di.component.DaggerUIComponent
import ru.nsu.currency.converter.di.component.UIComponent
import ru.nsu.currency.converter.domain.worker.CurrencyUpdateWorker
import java.util.concurrent.TimeUnit

class Application : Application() {

    companion object {
        private const val WORK_TAG = "currency_update_worker"
    }

    lateinit var uiComponent: UIComponent

    override fun onCreate() {
        super.onCreate()
        val dataComponent = DaggerDataComponent.builder().application(this).build()
        val domainComponent = DaggerDomainComponent.factory().create(dataComponent)
        uiComponent = DaggerUIComponent.factory().create(domainComponent)

        val workerFactory = domainComponent.provideWorkerFactory()

        val configuration = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

        WorkManager.initialize(this, configuration)

        val currencyUpdateWorkRequest =
            PeriodicWorkRequestBuilder<CurrencyUpdateWorker>(12, TimeUnit.HOURS)
                .setInitialDelay(0, TimeUnit.SECONDS)
                .addTag(WORK_TAG)
                .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                WORK_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                currencyUpdateWorkRequest
            )
    }
}