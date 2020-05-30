package com.sample.sampleweatherapp.main.repository

import com.sample.sampleweatherapp.data.remote.RestManager

class RemoteDataSource(private val restManager: RestManager) {

    fun getWeatherByCityName(params: Map<String, String>) = restManager.getWeatherByCityName(params)

}