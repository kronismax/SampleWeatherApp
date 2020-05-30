package com.sample.sampleweatherapp.main.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.sample.sampleweatherapp.R
import com.sample.sampleweatherapp.main.MainViewModel
import kotlinx.android.synthetic.main.fragment_weather.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WeatherFragment : Fragment() {

    private val viewModel: MainViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_weather, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.forecastLiveData.observe(viewLifecycleOwner, Observer {
            weatherTextView.text = it?.list?.first()?.getForecastString()
                forecastRecyclerView.adapter = ForecastAdapter(it.list) { forecast ->
                weatherTextView.text = forecast.getForecastString()
            }
        })
    }

}