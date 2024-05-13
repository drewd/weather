package com.dobson.weather.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dobson.weather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class HomeUiState {
    data class GoToDetails(val lat: Double, val long: Double) : HomeUiState()

    data object GoToSearch : HomeUiState()
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        refresh()
    }

    private fun refresh() {
        viewModelScope.launch {
            weatherRepository.getLastSearch().collectLatest { location ->
                _uiState.update {
                    location?.run { HomeUiState.GoToDetails(first, second) }
                        ?: HomeUiState.GoToSearch
                }
            }
        }
    }
}