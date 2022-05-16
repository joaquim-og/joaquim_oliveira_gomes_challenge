package com.joaquim_gomes_wit_challenge.views.ui.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.joaquim_gomes_wit_challenge.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.joaquim_gomes_wit_challenge.data.commom.extensions.mapWeatherIcon
import com.joaquim_gomes_wit_challenge.data.model.weather.ScreenWeatherInfo

@Composable
fun NoWeatherCardInfo(onTapNavigateBack: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize().clickable {
            onTapNavigateBack()
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(5.dp),
            style = MaterialTheme.typography.h4,
            text = stringResource(R.string.compose_ui_list_weather_info)
        )
    }
}

@Composable
fun WeatherCardInfo(cityWeatherInfo: ScreenWeatherInfo?, onTapCity: (ScreenWeatherInfo) -> Unit) {

    val composition by rememberLottieComposition(
        LottieCompositionSpec.Asset(
            cityWeatherInfo?.icon?.mapWeatherIcon() ?: ""
        )
    )

    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
            .background(color = colors.onBackground)
            .clickable {
                cityWeatherInfo?.let {
                    onTapCity(it)
                }
            }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            LottieAnimation(
                composition,
                modifier = Modifier.height(176.dp).fillMaxWidth(),
                restartOnPlay = true,
                iterations = Integer.MAX_VALUE
            )

            SetWeatherCardRowInfo(
                cityWeatherInfo?.nameCity ?: "",
                true,
                R.drawable.ic_baseline_location_city_24,
                stringResource(R.string.drawable_name_city_description)
            )

            SetWeatherCardRowInfo(
                stringResource(
                    R.string.general_text_place_holder_description_weather_temp
                ).replace("#weather_temp", cityWeatherInfo?.temperatureActual ?: ""),
                false,
                R.drawable.ic_baseline_adjust_24,
                stringResource(R.string.drawable_temp_city_description)
            )

            SetWeatherCardRowInfo(
                cityWeatherInfo?.date ?: "",
                false,
                R.drawable.ic_baseline_calendar_month_24,
                stringResource(R.string.drawable_date_city_description)
            )

            SetWeatherCardRowInfo(
                cityWeatherInfo?.descriptionWeather ?: "",
                false,
                R.drawable.ic_baseline_info_24,
                stringResource(R.string.drawable_weather_description_city_description)
            )
        }

    }
}
