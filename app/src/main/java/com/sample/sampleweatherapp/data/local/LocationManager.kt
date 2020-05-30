package com.sample.sampleweatherapp.data.local

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.sample.sampleweatherapp.model.location.LocationModel
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

private const val REQUEST_INTERVAL = 10000L
private const val REQUEST_FASTEST_INTERVAL = 5000L

class LocationManager(context: Context) {

    private val subject = PublishSubject.create<LocationModel>()
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val request: LocationRequest = LocationRequest.create().apply {
        interval = REQUEST_INTERVAL
        fastestInterval = REQUEST_FASTEST_INTERVAL
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private val callback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult?) {
            result?.locations?.forEach(::setLocation)
        }
    }

    val locationObservable: Flowable<LocationModel> =
        subject.toFlowable(BackpressureStrategy.MISSING)
            .doOnSubscribe { startLocationUpdates() }
            .doOnCancel { stopLocationUpdates() }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.lastLocation.addOnSuccessListener(::setLocation)
        fusedLocationClient.requestLocationUpdates(request, callback, Looper.getMainLooper())
    }

    private fun stopLocationUpdates() = fusedLocationClient.removeLocationUpdates(callback)

    private fun setLocation(location: Location) =
        subject.onNext(LocationModel(location.longitude, location.latitude))
}

