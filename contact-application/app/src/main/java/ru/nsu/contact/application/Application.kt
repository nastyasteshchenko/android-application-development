package ru.nsu.contact.application

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import ru.nsu.contact.application.di.component.DaggerDataComponent
import ru.nsu.contact.application.di.component.DaggerDomainComponent
import ru.nsu.contact.application.di.component.DaggerUIComponent
import ru.nsu.contact.application.di.component.UIComponent

class Application : Application() {

    lateinit var uiComponent: UIComponent

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        val dataComponent = DaggerDataComponent.builder().application(this).build()
        val domainComponent = DaggerDomainComponent.factory().create(dataComponent)
        uiComponent = DaggerUIComponent.factory().create(domainComponent)
    }

}