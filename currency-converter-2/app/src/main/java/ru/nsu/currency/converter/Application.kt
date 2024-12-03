package ru.nsu.currency.converter

import android.app.Application
import ru.nsu.currency.converter.di.component.DaggerDataComponent
import ru.nsu.currency.converter.di.component.DaggerDomainComponent
import ru.nsu.currency.converter.di.component.DaggerUIComponent
import ru.nsu.currency.converter.di.component.UIComponent

class Application : Application() {

    lateinit var uiComponent: UIComponent

    override fun onCreate() {
        super.onCreate()
        val dataComponent = DaggerDataComponent.builder().application(this).build()
        val domainComponent = DaggerDomainComponent.factory().create(dataComponent)
        uiComponent = DaggerUIComponent.factory().create(domainComponent)
    }

}