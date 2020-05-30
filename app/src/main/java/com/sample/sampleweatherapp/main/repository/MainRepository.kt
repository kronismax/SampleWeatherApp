package com.sample.sampleweatherapp.main.repository

class MainRepository(private val remote: RemoteDataSource, private val local: LocalDataSource) {

    fun getWeather(params: Map<String, String>) = remote.getWeatherByCityName(params)

    fun getCachedCitiesLivaData() = local.getCachedCities()

    fun getLocation() = local.getLocation()
}