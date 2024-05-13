package com.dobson.weather.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import com.dobson.weather.repository.model.Weather
import com.dobson.weather.repository.remote.service.WeatherService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class WeatherRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val service: WeatherService,
) {

    suspend fun get(latitude: Double, longitude: Double): Weather {
        val weather = service.get(latitude, longitude)
        setLastSearch(latitude, longitude)
        return weather
    }

    // Simplifying this with a Pair, should use proto to capture the data together
    fun getLastSearch(): Flow<Pair<Double, Double>?> {
        return dataStore.data.map {
            val lat = it[KEY_LAST_SEARCH_LAT]
            val lon = it[KEY_LAST_SEARCH_LON]
            return@map if (lat != null && lon != null) {
                Pair(lat, lon)
            } else null
        }
    }

    private suspend fun setLastSearch(lat: Double, lon: Double) {
        dataStore.edit {
            it[KEY_LAST_SEARCH_LAT] = lat
            it[KEY_LAST_SEARCH_LON] = lon
        }
    }


    companion object {
        private val KEY_LAST_SEARCH_LAT = doublePreferencesKey("LAST_SEARCH_LAT")
        private val KEY_LAST_SEARCH_LON = doublePreferencesKey("LAST_SEARCH_LON")
    }
}