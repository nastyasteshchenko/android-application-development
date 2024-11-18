package ru.nsu.contact.application

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import ru.nsu.contact.application.di.component.AppComponent
import ru.nsu.contact.application.di.component.DaggerAppComponent
import ru.nsu.contact.application.di.module.ProvidesDataModule

class Application : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        appComponent = DaggerAppComponent.builder()
            .providesDataModule(ProvidesDataModule(this))
            .build()
        appComponent.inject(this)
    }

}