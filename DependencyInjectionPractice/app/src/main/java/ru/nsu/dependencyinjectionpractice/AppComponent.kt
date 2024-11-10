package ru.nsu.dependencyinjectionpractice

import dagger.Component
import ru.nsu.dependencyinjectionpractice.data.module.DataModule
import ru.nsu.dependencyinjectionpractice.domain.module.DomainModule
import ru.nsu.dependencyinjectionpractice.ui.MainActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface AppComponent {

    fun inject(activity: MainActivity)
}