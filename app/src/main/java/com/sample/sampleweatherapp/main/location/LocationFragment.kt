package com.sample.sampleweatherapp.main.location

import android.Manifest
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.location.LocationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.extension.addListener
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.sample.sampleweatherapp.R
import com.sample.sampleweatherapp.data.remote.response.ForecastResponse
import com.sample.sampleweatherapp.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_location.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import timber.log.Timber


class LocationFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_location, container, false)

    override fun onResume() {
        super.onResume()
        requestLocationPermission()
    }

    private fun requestLocationPermission() {
        val request = permissionsBuilder(Manifest.permission.ACCESS_FINE_LOCATION).build()
        request.addListener { result ->
            when {
                result.allGranted() -> requestEnableGps()
                else -> requestLocationPermission()
            }
        }
        request.send()
    }

    private fun requestEnableGps() {
        val manager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (LocationManagerCompat.isLocationEnabled(manager)) {
            requestEnableWifi()
        } else {
            AlertDialog.Builder(requireContext()).setTitle(getString(R.string.enableGpsTitle))
                .setPositiveButton(
                    android.R.string.ok
                ) { _, _ -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) }
                .setCancelable(false).show()
        }
    }

    private fun requestEnableWifi() {
        val cm =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetworkInfo.isConnectedOrConnecting) {
            getWeather()
        } else {
            startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
        }
    }

    private fun getWeather() {
        viewModel.forecastLiveData.observe(viewLifecycleOwner, Observer {
            val map = forecastMapper(it)
            locationTextView.text = map.first
            Glide.with(this).load(map.second).into(forecastImageView)
            temperatureTextView.text = map.third
        })
        viewModel.citiesLiveData.observe(viewLifecycleOwner, Observer { cities ->
            citiesGridView.removeAllViews()
            val spec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL, 1f)
            cities.forEach { city ->
                val view = TextView(requireContext()).apply { text = city.name }
                view.setOnClickListener {
                    viewModel.getWeatherByCity(city.name)
                    Timber.d(city.name)
                }
                citiesGridView.addView(view, GridLayout.LayoutParams(spec, spec))
            }

        })
    }

    private fun forecastMapper(it: ForecastResponse): Triple<String, String, String> {
        val today = it.list.firstOrNull()
        val iconUrl = today?.weather?.firstOrNull()?.getIconUrl() ?: ""
        return Triple(it.city.name, iconUrl, "${today?.temp?.getMinMax()}")
    }
}