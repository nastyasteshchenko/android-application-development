package ru.nsu.dependencyinjectionpractice.di.module

import dagger.Binds
import dagger.Module
import ru.nsu.dependencyinjectionpractice.data.datasource.SampleStringDataSource
import ru.nsu.dependencyinjectionpractice.data.datasource.SampleStringLocalDataSource
import ru.nsu.dependencyinjectionpractice.data.datasource.SampleStringRemoteDataSource
import javax.inject.Qualifier

@Module
interface DataModule {

    @Binds
    @Local
    fun bindStringLocalDataSource(localDataSource: SampleStringLocalDataSource):
            SampleStringDataSource

    @Binds
    @Remote
    fun bindStringRemoteDataSource(remoteDataSource: SampleStringRemoteDataSource):
            SampleStringDataSource
}

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Local

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Remote

