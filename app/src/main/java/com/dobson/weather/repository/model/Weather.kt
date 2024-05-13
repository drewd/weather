package com.dobson.weather.repository.model

import com.dobson.weather.BuildConfig
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Weather(
//    val coord
    val weather: List<Condition>,
    val main: BasicInfo,
    val visibility: Int,
    val wind: WindInfo,
    val clouds: CloudInfo,
    // TODO rain
    // TODO snow
    @Json(name = "dt") val timestamp: Long,
    val timezone: Long,
    val sys: SysInfo,
    val name: String,
)

@JsonClass(generateAdapter = true)
data class Condition(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String,
) {

    val url: String
        get() {
            return "https://openweathermap.org/img/wn/${icon}@2x.png"
        }
}

@JsonClass(generateAdapter = true)
data class BasicInfo(
    val temp: Double,
    @Json(name = "feels_like") val feelsLike: Double,
    val pressure: Int,
    val humidity: Int,
    @Json(name = "temp_min") val minimum: Double? = null,
    @Json(name = "temp_max") val maximum: Double? = null,
    @Json(name = "sea_level") val seaLevel: Int? = null,
    @Json(name = "grnd_level") val groundLevel: Int? = null,
)

@JsonClass(generateAdapter = true)
data class WindInfo(
    val speed: Double,
    val deg: Int,
    val gust: Float? = null,
)

@JsonClass(generateAdapter = true)
data class CloudInfo(
    val all: Int,
)

@JsonClass(generateAdapter = true)
data class SysInfo(
    val country: String,
    val sunrise: Long,
    val sunset: Long,
)