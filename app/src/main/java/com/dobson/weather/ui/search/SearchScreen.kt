package com.dobson.weather.ui.search

import android.Manifest
import android.annotation.SuppressLint
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.dobson.weather.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    uiState: SearchUiState,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    onGeocodeSelected: (Double, Double) -> Unit,
    onGoToMyLocation: () -> Unit,
    onPermissionDenied: () -> Unit,
) {
    var isActive by remember { mutableStateOf(true) }

    val locationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onGoToMyLocation()
        }
    }
    Scaffold(
        topBar = {
            SearchBar(
                query = uiState.searchText ?: "",
                onQueryChange = onQueryChange,
                onSearch = onSearch,
                active = isActive,
                onActiveChange = { value -> isActive = value },
                trailingIcon = {
                    IconButton(onClick = {
                        when {
                            locationPermissionState.status.isGranted -> {
                                onGoToMyLocation()
                            }

                            locationPermissionState.status.shouldShowRationale -> {
                                onPermissionDenied()
                            }

                            else -> {
                                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_my_location),
                            contentDescription = stringResource(R.string.my_location)
                        )
                    }
                }

            ) {
                LazyColumn {
                    items(uiState.results) {
                        Text(text = "${it.name}, ${it.state ?: ""} ${it.country}",
                            modifier = Modifier.clickable {
                                onGeocodeSelected(it.lat, it.lon)
                            })
                    }
                }
            }
        },
    ) {
        // TODO maybe show recently searched or saved locations here?
    }
}