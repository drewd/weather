package com.dobson.weather.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.dobson.weather.repository.model.Weather
import com.dobson.weather.repository.remote.service.WeatherService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherRepositoryTest {
    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK(relaxed = true)
    lateinit var mockDataStore: DataStore<Preferences>

    @MockK
    lateinit var mockWeatherService: WeatherService

    @MockK
    lateinit var mockWeather: Weather

    @InjectMockKs
    lateinit var repository: WeatherRepository

    @Before
    fun setUp() {
        coEvery { mockWeatherService.get(any(), any()) } returns mockWeather
    }

    @Test
    fun `get saves search`() = runBlocking {
        val mockLat = 1.toDouble()
        val mockLon = 2.toDouble()
        assertEquals(repository.get(mockLat, mockLon), mockWeather)
        coVerify { mockWeatherService.get(mockLat, mockLon) }
        coVerify { mockDataStore.updateData(any()) }
    }
}