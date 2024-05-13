package com.dobson.weather.repository.remote.service

import com.dobson.weather.repository.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("/data/2.5/weather")
    suspend fun get(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        // Defaulting to imperial, this should be a setting somewhere in the app
        @Query("units") units: String? = "imperial",
        // Defaulting to english, this should be pulled from the device settings
        @Query("lang") lang: String? = "en",
    ): Weather
}