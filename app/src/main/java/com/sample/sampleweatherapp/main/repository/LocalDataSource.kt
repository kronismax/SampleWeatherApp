package com.sample.sampleweatherapp.main.repository

import com.sample.sampleweatherapp.data.local.DBManager
import com.sample.sampleweatherapp.data.local.LocationManager

class LocalDataSource(private val dbManager: DBManager, private val locationManager: LocationManager) {

    fun getCachedCities() = dbManager.getCityDAO().getAll()

    fun getLocation() = locationManager.locationObservable
}