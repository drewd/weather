package com.dobson.weather.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dobson.weather.ui.details.DetailsRoute
import com.dobson.weather.ui.home.HomeRoute
import com.dobson.weather.ui.search.SearchRoute
import com.dobson.weather.ui.theme.WeatherTheme

@Composable
fun MainApp(
    context: Context = LocalContext.current,
) {
    WeatherTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val mainNavController = rememberMainNavController()
            NavHost(
                navController = mainNavController.navHostController,
                startDestination = MainDestinations.ROUTE_HOME,
            ) {
                mainNavGraph(
                    navigateToDetails = mainNavController::navigateToDetails,
                    navigateToSearch = mainNavController::navigateToSearch,
                    navigateToAppSettings = {
                        context.navigateToAppSettings()
                    }
                )
            }
        }
    }
}

private fun NavGraphBuilder.mainNavGraph(
    navigateToDetails: (Double, Double, NavBackStackEntry) -> Unit,
    navigateToSearch: (NavBackStackEntry) -> Unit,
    navigateToAppSettings: () -> Unit,
) {

    composable(
        route = MainDestinations.ROUTE_HOME,
    ) {
        HomeRoute(
            navigateToDetails = { lat, lon -> navigateToDetails(lat, lon, it) },
            navigateToSearch = { navigateToSearch(it) }
        )
    }

    composable(
        route = MainDestinations.ROUTE_SEARCH,
    ) {
        SearchRoute(
            onGeocodeSelected = { lat, lon -> navigateToDetails(lat, lon, it) },
            onPermissionDenied = navigateToAppSettings,
        )
    }

    composable(
        route = "${MainDestinations.ROUTE_DETAILS}/{${MainDestinations.KEY_LAT}}/{${MainDestinations.KEY_LON}}",
        arguments = listOf(
            navArgument(MainDestinations.KEY_LAT) {
                type = NavType.FloatType
            },
            navArgument(MainDestinations.KEY_LON) {
                type = NavType.FloatType
            },
        )
    ) { from ->
        DetailsRoute(
            navigateToSearch = { navigateToSearch(from) }
        )
    }
}

fun Context.navigateToAppSettings() {
    this.startActivity(
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", this.packageName, null)
        )
    )
}