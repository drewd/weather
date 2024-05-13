package com.dobson.weather.repository.remote.service

import com.dobson.weather.repository.model.Geocode
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodeService {

    @GET("/geo/1.0/direct")
    suspend fun get(
        @Query("q") query: String,
        @Query("limit") limit: Int? = null
    ): List<Geocode>
}