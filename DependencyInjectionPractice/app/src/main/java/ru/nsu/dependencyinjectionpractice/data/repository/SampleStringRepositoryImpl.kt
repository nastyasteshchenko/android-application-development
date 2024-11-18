package ru.nsu.dependencyinjectionpractice.data.repository

import ru.nsu.dependencyinjectionpractice.data.datasource.SampleStringDataSource
import ru.nsu.dependencyinjectionpractice.di.module.Local
import ru.nsu.dependencyinjectionpractice.di.module.Remote
import ru.nsu.dependencyinjectionpractice.domain.repository.SampleStringRepository
import javax.inject.Inject

class SampleStringRepositoryImpl @Inject constructor(
    @Local private val localDataSource: SampleStringDataSource,
    @Remote private val remoteDataSource: SampleStringDataSource
) : SampleStringRepository {

    override fun getFromRemote(): String =
        remoteDataSource.get()

    override fun getFromLocal(): String =
        localDataSource.get()
}