package ru.nsu.dependencyinjectionpractice

import android.app.Application
import ru.nsu.dependencyinjectionpractice.di.component.AppComponent
import ru.nsu.dependencyinjectionpractice.di.component.DaggerAppComponent

class HomeworkApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}