package com.sample.sampleweatherapp.data.remote.response

data class ForecastResponse(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<Forecast>,
    val message: Double
)

data class City(
    val country: String,
    val geoname_id: Int,
    val iso2: String,
    val lat: Double,
    val lon: Double,
    val name: String,
    val population: Int,
    val type: String
)

data class Forecast(
    val clouds: Int,
    val deg: Int,
    val dt: Long,
    val humidity: Int,
    val pressure: Double,
    val snow: Double,
    val speed: Double,
    val temp: Temp,
    val weather: List<Weather>
) {
    fun getForecastString() = "clouds: $clouds\n" +
            "deg: $deg\n" +
            "dt: $dt\n" +
            "humidity: $humidity\n" +
            "pressure: $pressure\n" +
            "snow: $snow\n" +
            "speed: $speed\n" +
            "temp: $temp"
}

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
) {
    fun getMinMax() = "${min.toInt()}°C/${max.toInt()}°C"
}

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) {
  fun getIconUrl() = "http://openweathermap.org/img/wn/$icon.png"
}