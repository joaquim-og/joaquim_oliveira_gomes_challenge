package com.joaquim_gomes_wit_challenge.views.ui.compose.previews

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.joaquim_gomes_wit_challenge.views.ui.compose.components.NoWeatherCardInfo
import com.joaquim_gomes_wit_challenge.views.ui.compose.components.WeatherCardInfo

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun NoWeatherCardInfo_Light_Preview() {
    NoWeatherCardInfo(onTapNavigateBack = {})
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun NoWeatherCardInfo_Night_Preview() {
    NoWeatherCardInfo(onTapNavigateBack = {})
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
fun WeatherCardInfo_Light_Preview() {
    WeatherCardInfo(cityWeatherInfo = mockCityWeatherInfo, onTapCity = {})
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun WeatherCardInfo_Night_Preview() {
    WeatherCardInfo(cityWeatherInfo = mockCityWeatherInfo, onTapCity = {})
}