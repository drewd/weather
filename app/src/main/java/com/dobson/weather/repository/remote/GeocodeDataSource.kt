package com.dobson.weather.repository.remote

import com.dobson.weather.repository.model.Geocode
import com.dobson.weather.repository.remote.service.GeocodeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeocodeDataSource @Inject constructor(private val service: GeocodeService) {

    suspend fun get(query: String): List<Geocode> = withContext(Dispatchers.IO) {
        service.get("${query},US", limit = 5)
    }
}