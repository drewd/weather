package com.dobson.weather.repository.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Geocode(
    val name: String,
    val lat: Double,
    val lon: Double,
    val country: String,
    val state: String? = null
)
