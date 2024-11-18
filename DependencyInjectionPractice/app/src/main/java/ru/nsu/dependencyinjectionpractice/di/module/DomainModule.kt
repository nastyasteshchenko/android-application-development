package ru.nsu.dependencyinjectionpractice.di.module

import dagger.Binds
import dagger.Module
import ru.nsu.dependencyinjectionpractice.data.repository.SampleStringRepositoryImpl
import ru.nsu.dependencyinjectionpractice.domain.repository.SampleStringRepository
import javax.inject.Singleton

@Module
interface DomainModule {

    @Binds
    @Singleton
    fun bindStringLocalDataSource(sampleStringRepositoryImpl: SampleStringRepositoryImpl):
            SampleStringRepository
}