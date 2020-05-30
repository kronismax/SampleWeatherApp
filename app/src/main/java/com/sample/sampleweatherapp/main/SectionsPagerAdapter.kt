package com.sample.sampleweatherapp.main

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sample.sampleweatherapp.R
import com.sample.sampleweatherapp.main.location.LocationFragment
import com.sample.sampleweatherapp.main.weather.WeatherFragment

private val TAB_TITLES = arrayOf(R.string.locationFragmentTitle, R.string.weatherFragmentTitle)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int) = when (position) {
        0 -> LocationFragment()
        else -> WeatherFragment()
    }

    override fun getPageTitle(position: Int) = context.resources.getString(TAB_TITLES[position])

    override fun getCount() = 2
}