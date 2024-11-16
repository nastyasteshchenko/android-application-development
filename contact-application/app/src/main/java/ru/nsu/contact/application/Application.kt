package ru.nsu.contact.application

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import ru.nsu.contact.application.di.component.AppComponent
import ru.nsu.contact.application.di.component.DaggerAppComponent
import ru.nsu.contact.application.di.module.DataModule

class Application : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        appComponent = DaggerAppComponent.builder()
            .dataModule(DataModule(this))
            .build()
        appComponent.inject(this)
    }
}