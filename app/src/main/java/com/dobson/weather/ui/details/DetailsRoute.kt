package com.dobson.weather.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailsRoute(
    viewModel: DetailsViewModel = hiltViewModel(),
    navigateToSearch: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // TODO simplicity, need an error state here
    uiState.weather?.let {
        DetailsScreen(
            weather = it,
            navigateToSearch = navigateToSearch,
        )
    }
}