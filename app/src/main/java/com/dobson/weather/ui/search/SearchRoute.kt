package com.dobson.weather.ui.search

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SearchRoute(
    viewModel: SearchViewModel = hiltViewModel(),
    onGeocodeSelected: (Double, Double) -> Unit,
    onPermissionDenied: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    SearchScreen(
        uiState = uiState,
        onQueryChange = viewModel::onChange,
        onSearch = viewModel::onSearch,
        onGeocodeSelected = onGeocodeSelected,
        onGoToMyLocation = { viewModel.goToMyLocation(onGeocodeSelected) },
        onPermissionDenied = onPermissionDenied
    )
}