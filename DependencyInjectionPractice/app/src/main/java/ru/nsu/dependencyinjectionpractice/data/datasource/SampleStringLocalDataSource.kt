package ru.nsu.dependencyinjectionpractice.data.datasource

import javax.inject.Inject

class SampleStringLocalDataSource @Inject constructor() : SampleStringDataSource {

	private val mockedCache = "String from local data source"

	override fun get(): String = mockedCache
}