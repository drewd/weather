package com.dobson.weather.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dobson.weather.repository.model.Geocode


object MainDestinations {
    const val ROUTE_DETAILS = "details"
    const val ROUTE_HOME = "home"
    const val ROUTE_SEARCH = "search"

    const val KEY_LAT = "latitude"
    const val KEY_LON = "longitude"
}

@Composable
fun rememberMainNavController(
    navHostController: NavHostController = rememberNavController()
): MainNavigation = remember(navHostController) {
    MainNavigation(navHostController)
}

@Stable
class MainNavigation(val navHostController: NavHostController) {
    fun navigateToDetails(latitude: Double, longitude: Double, from: NavBackStackEntry) {
        if (from.lifecycle.currentState == Lifecycle.State.RESUMED) {
            navHostController.navigate("${MainDestinations.ROUTE_DETAILS}/${latitude}/${longitude}") {
                popUpTo("${MainDestinations.ROUTE_DETAILS}/{${MainDestinations.KEY_LAT}}/{${MainDestinations.KEY_LON}}") {
                    inclusive = true
                }
            }
        }
    }

    fun navigateToSearch(from: NavBackStackEntry) {
        if (from.lifecycle.currentState == Lifecycle.State.RESUMED) {
            navHostController.navigate(MainDestinations.ROUTE_SEARCH) {
                popUpTo(MainDestinations.ROUTE_SEARCH) {
                    inclusive = true
                }
            }
        }
    }
}