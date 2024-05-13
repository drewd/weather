package com.dobson.weather.ui.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dobson.weather.ui.home.HomeUiState.GoToDetails
import com.dobson.weather.ui.home.HomeUiState.GoToSearch

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToDetails: (Double, Double) -> Unit,
    navigateToSearch: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState) {
        is GoToDetails -> {
            val details = uiState as GoToDetails
            navigateToDetails(details.lat, details.long)
        }

        is GoToSearch -> {
            navigateToSearch()
        }

        null -> {}
    }

}