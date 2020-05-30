package com.sample.sampleweatherapp.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sample.sampleweatherapp.BuildConfig
import com.sample.sampleweatherapp.data.remote.ApiService.Companion.API_KEY
import com.sample.sampleweatherapp.utils.HttpLogger
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestManager {

    companion object {
        private const val TIME_OUT = 30L
        private const val APP_ID_KEY = "appid"

    }

    private lateinit var api: ApiService
    private lateinit var gson: Gson
    private val appIdOptions = mutableMapOf(APP_ID_KEY to API_KEY, "units" to "metric", "cnt" to "16")

    init {
        initServices(createRetrofit())
    }

    private fun initServices(retrofit: Retrofit) {
        api = retrofit.create(ApiService::class.java)
    }

    private fun createRetrofit() = Retrofit.Builder().apply {
        baseUrl(ApiService.SERVER)
        addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
        addConverterFactory(createGsonConverter())
        client(createClient())
    }.build()

    private fun createClient() = OkHttpClient.Builder().apply {
        addInterceptor {
            it.proceed(it.request())
        }
        connectTimeout(TIME_OUT, TimeUnit.SECONDS)
        writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        readTimeout(TIME_OUT, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            interceptors().add(HttpLogger().apply {
                level = HttpLogger.Level.BODY
            })
        }
    }.build()

    private fun createGsonConverter() = GsonConverterFactory.create(
        GsonBuilder().apply { setLenient() }.create().also { gson = it })

    fun getWeatherByCityName(params: Map<String, String>) =
        api.getWeatherByCityName(appIdOptions.apply { putAll(params) })

}