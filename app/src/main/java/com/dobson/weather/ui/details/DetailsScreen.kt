package com.dobson.weather.ui.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.dobson.weather.R
import com.dobson.weather.repository.model.BasicInfo
import com.dobson.weather.repository.model.CloudInfo
import com.dobson.weather.repository.model.Condition
import com.dobson.weather.repository.model.SysInfo
import com.dobson.weather.repository.model.Weather
import com.dobson.weather.repository.model.WindInfo
import com.dobson.weather.ui.theme.WeatherTheme
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun DetailsScreen(
    weather: Weather,
    navigateToSearch: () -> Unit,
) {
    val dateFormat = SimpleDateFormat(
        "h:mm a", Locale.getDefault()
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = weather.name) },
                navigationIcon = {
                    IconButton(onClick = { navigateToSearch() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                }
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            Row {
                Text(text = "${weather.main.temp.roundToInt()}°")
                GlideImage(
                    model = weather.weather.firstOrNull()?.url,
                    contentDescription = stringResource(R.string.current_conditions),
                )
                Column {
                    weather.weather.firstOrNull()?.main?.let {
                        Text(text = it)
                    }
                    Text(
                        text = stringResource(
                            R.string.feels_like,
                            weather.main.feelsLike.roundToInt()
                        )
                    )
                }
            }
            Row {
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    Header(text = stringResource(R.string.sunrise))
                    Text(text = dateFormat.format(weather.sys.sunrise.seconds.inWholeMilliseconds))
                }
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    Header(text = "Sunset")
                    Text(text = dateFormat.format(weather.sys.sunset.seconds.inWholeMilliseconds))
                }
            }
            Row {
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    Header(text = stringResource(R.string.sky_condition))
                    Text(
                        text = weather.weather.firstOrNull()?.description
                            ?: stringResource(R.string.unknown)
                    )
                }
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    Header(text = stringResource(R.string.humidity))
                    Text(text = stringResource(R.string.humidity_percentage, weather.main.humidity))
                }
            }
            Row {
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    Header(text = stringResource(R.string.visibility))
                    Text(
                        text = stringResource(
                            R.string.visibility_imperial,
                            weather.visibility / 1609
                        )
                    )
                }
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    Header(text = stringResource(R.string.wind_direction))
                    Text(
                        text = stringArrayResource(id = R.array.directions)[weather.wind.deg.mod(360)
                            .div(22.5)
                            .roundToInt() + 1]
                    )
                }
            }
            Row {
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    Header(text = stringResource(R.string.pressure))
                    Text(
                        text = stringResource(
                            R.string.pressure_imperial, 29.92.times(weather.main.pressure)
                                .div(1013.2)
                                .toBigDecimal()
                                .setScale(2, RoundingMode.HALF_UP)
                        )
                    )
                }
                Column(
                    modifier = Modifier.weight(1F)
                ) {
                    Header(text = stringResource(R.string.wind_speed))
                    Text(
                        text = stringResource(
                            R.string.wind_speed_imperial,
                            weather.wind.speed.roundToInt()
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun Header(text: String) {
    Text(
        text = text, style = MaterialTheme.typography.labelLarge
    )
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewDetailsScreen() {
    WeatherTheme {
        DetailsScreen(
            weather = Weather(
                weather = listOf(
                    Condition(
                        id = 802, main = "Clouds",//
                        description = "scattered clouds", icon = "03d"
                    )
                ), main = BasicInfo(
                    temp = 84.27, //
                    feelsLike = 88.27,//
                    minimum = 83.25,
                    maximum = 84.27,
                    pressure = 1010,//
                    humidity = 61,
                ), visibility = 10000,//
                wind = WindInfo(
                    speed = 8.05, //
                    deg = 70,//
                ), clouds = CloudInfo(
                    all = 40,
                ), sys = SysInfo(
                    country = "US",
                    sunrise = 1715254344,//
                    sunset = 1715303691,//
                ), timezone = -18000, timestamp = 1714278151, name = "USA"
            ),
            navigateToSearch = {},
        )
    }
}