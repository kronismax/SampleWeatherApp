package com.sample.sampleweatherapp.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sample.sampleweatherapp.data.remote.response.ForecastResponse
import com.sample.sampleweatherapp.main.repository.MainRepository
import com.sample.sampleweatherapp.model.location.LocationModel
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import org.koin.core.KoinComponent
import timber.log.Timber

class MainViewModel(private val repository: MainRepository) : ViewModel(), KoinComponent {

    private var disposable: Disposable
    private val _forecastLiveData = MutableLiveData<ForecastResponse>()
    val forecastLiveData: LiveData<ForecastResponse> = _forecastLiveData
    val citiesLiveData = repository.getCachedCitiesLivaData()

    init {
        disposable = repository
            .getLocation()
            .flatMap(::mapToForecast)
            .take(1)
            .subscribe(_forecastLiveData::postValue)
    }

    private fun mapToForecast(locationModel: LocationModel): Flowable<ForecastResponse> {
        val params = mapOf("lat" to "${locationModel.latitude}", "lon" to "${locationModel.longitude}")
        return repository.getWeather(params).distinctUntilChanged()
    }

    fun getWeatherByCity(city: String) {
        disposable.dispose()
        disposable =
            repository.getWeather(mapOf("q" to city)).subscribe(_forecastLiveData::postValue)
    }

    override fun onCleared() {
        super.onCleared()
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

}