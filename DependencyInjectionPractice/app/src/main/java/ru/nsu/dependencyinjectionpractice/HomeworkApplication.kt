package ru.nsu.dependencyinjectionpractice

import android.app.Application

class HomeworkApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}