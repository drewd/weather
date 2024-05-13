package com.dobson.weather.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dobson.weather.repository.WeatherRepository
import com.dobson.weather.repository.model.Weather
import com.dobson.weather.ui.MainDestinations
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailsUiState(
    val weather: Weather? = null,
    val loading: Boolean = false,
    val error: Boolean = false,
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: WeatherRepository,
) : ViewModel() {

    private val latitude = checkNotNull<Double>(savedStateHandle[MainDestinations.KEY_LAT])
    private val longitude = checkNotNull<Double>(savedStateHandle[MainDestinations.KEY_LON])
    private val _uiState = MutableStateFlow(
        DetailsUiState(
            loading = true,
        )
    )
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        _uiState.update {
            it.copy(
                loading = true,
                error = false
            )
        }
        viewModelScope.launch {
            runCatching {
                repository.get(latitude, longitude)
            }.onSuccess { weather ->
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = false,
                        weather = weather
                    )
                }
            }.onFailure {
                // Show more error states
                _uiState.update {
                    it.copy(
                        loading = false,
                        error = true,
                        weather = null,
                    )
                }
            }
        }
    }

}