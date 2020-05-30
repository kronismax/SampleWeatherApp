package com.sample.sampleweatherapp.main.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sample.sampleweatherapp.R
import com.sample.sampleweatherapp.data.remote.response.Forecast
import kotlinx.android.synthetic.main.forecast_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class ForecastAdapter(
    private var items: List<Forecast>,
    private val onClick: (Forecast) -> Unit
) : RecyclerView.Adapter<ForecastViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ForecastViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false), onClick
    )

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

class ForecastViewHolder(
    itemView: View,
    private val onClick: (Forecast) -> Unit
) : RecyclerView.ViewHolder(itemView) {
    fun bind(forecast: Forecast) {
        val s = forecast.weather.firstOrNull()?.getIconUrl()
        Glide.with(itemView.context).load(s).into(itemView.forecastIconImageView)
        val instance = Calendar.getInstance()
        instance.timeInMillis = forecast.dt * 1000L
        itemView.dateTextView.text =
            SimpleDateFormat("dd MMM", Locale.getDefault()).format(instance.time)
        itemView.tempForecastTextView.text = forecast.temp.getMinMax()
        itemView.setOnClickListener { onClick(forecast) }
    }

}