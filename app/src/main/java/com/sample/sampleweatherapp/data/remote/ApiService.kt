package com.sample.sampleweatherapp.data.remote

import com.sample.sampleweatherapp.data.remote.response.ForecastResponse
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiService {

    companion object {

        private const val SCHEME = "https://"
        private const val HOSTNAME = "api.openweathermap.org"

        const val SERVER = "$SCHEME$HOSTNAME"
        const val FORECAST = "/data/2.5/forecast/daily?"
        const val API_KEY = "117d457f872c098c42b74c88d000d09b"
    }

    @GET(FORECAST)
    fun getWeatherByCityName(@QueryMap params: Map<String, String>): Flowable<ForecastResponse>
}