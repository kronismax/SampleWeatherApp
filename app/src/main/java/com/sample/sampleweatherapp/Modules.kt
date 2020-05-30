package com.sample.sampleweatherapp

import com.google.android.gms.location.LocationServices
import com.sample.sampleweatherapp.data.local.DBManager
import com.sample.sampleweatherapp.data.local.LocationManager
import com.sample.sampleweatherapp.data.remote.RestManager
import com.sample.sampleweatherapp.main.MainViewModel
import com.sample.sampleweatherapp.main.repository.LocalDataSource
import com.sample.sampleweatherapp.main.repository.RemoteDataSource
import com.sample.sampleweatherapp.main.repository.MainRepository
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object Modules {

    fun getListOfModules() = mutableListOf(managerModule, mainModule)

    private val managerModule = module(true) {
        single { DBManager(androidApplication()) }
        single { RestManager() }
    }

    private val mainModule = module {
        viewModel { MainViewModel(get()) }
        factory { MainRepository(get(), get()) }
        factory { RemoteDataSource(get()) }
        factory { LocalDataSource(get(), get()) }
        factory { LocationServices.getFusedLocationProviderClient(androidApplication()) }
        factory { LocationManager(androidApplication()) }
    }

}