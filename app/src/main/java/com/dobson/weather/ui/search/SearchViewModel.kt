package com.dobson.weather.ui.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dobson.weather.repository.LocationRepository
import com.dobson.weather.repository.model.Geocode
import com.dobson.weather.repository.remote.GeocodeDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SearchUiState(
    val loading: Boolean = false,
    val searchText: String? = null,
    val results: List<Geocode> = emptyList(),
)

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val locationRepository: LocationRepository,
    private val dataSource: GeocodeDataSource,
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        SearchUiState(
            loading = false
        )
    )
    val uiState = _uiState.asStateFlow()

    fun onChange(text: String) {
        _uiState.update {
            it.copy(
                searchText = text
            )
        }
        onSearch(text)
    }

    fun onSearch(text: String) {
        if (text.isNotEmpty()) {
            viewModelScope.launch {
                _uiState.update { it.copy(loading = true) }
                runCatching { dataSource.get(text) }
                    .onSuccess { results ->
                        _uiState.update {
                            it.copy(
                                loading = false,
                                results = results
                            )
                        }
                    }
                    .onFailure { throwable ->
                        Log.w("DREW", throwable)
                        _uiState.update {
                            it.copy(
                                loading = false,
                                results = emptyList()
                            )
                        }
                    }
            }
        }
    }

    fun goToMyLocation(callback: (Double, Double) -> Unit) {
        viewModelScope.launch {
            val locationDeferred = async { locationRepository.getCurrentLocation() }
            locationDeferred.await()?.let { location ->
                callback(location.latitude, location.longitude)
            }
        }
    }
}