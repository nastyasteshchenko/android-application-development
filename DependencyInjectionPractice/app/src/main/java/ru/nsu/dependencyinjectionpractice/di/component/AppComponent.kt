package ru.nsu.dependencyinjectionpractice.di.component

import dagger.Component
import ru.nsu.dependencyinjectionpractice.di.module.DataModule
import ru.nsu.dependencyinjectionpractice.di.module.DomainModule
import ru.nsu.dependencyinjectionpractice.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
}