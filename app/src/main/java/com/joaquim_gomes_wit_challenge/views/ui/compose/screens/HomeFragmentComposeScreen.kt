package com.joaquim_gomes_wit_challenge.views.ui.compose.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joaquim_gomes_wit_challenge.data.model.weather.ScreenWeatherInfo
import com.joaquim_gomes_wit_challenge.views.ui.compose.components.NoWeatherCardInfo
import com.joaquim_gomes_wit_challenge.views.ui.compose.components.WeatherCardInfo

@Composable
fun HomeFragmentComposeScreen(
    listWeatherInfo: List<ScreenWeatherInfo?>,
    onTapCity: (ScreenWeatherInfo) -> Unit,
    onTapNavigateBack: () -> Unit
) {

    AnimatedVisibility(
        visible = true,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            RenderHomeFragmentBodyContent(
                listWeatherInfo,
                onTapCity = onTapCity,
                onTapNavigateBack = onTapNavigateBack
            )
        }
    }
}

@Composable
fun RenderHomeFragmentBodyContent(
    listWeatherInfo: List<ScreenWeatherInfo?>,
    onTapCity: (ScreenWeatherInfo) -> Unit,
    onTapNavigateBack: () -> Unit
) {

    val listState = rememberLazyListState()

    LazyColumn(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(5.dp),
        state = listState
    ) {

        listWeatherInfo.let { listWeather ->
            if (listWeather.isNotEmpty()) {
                items(items = listWeather) { cityWeather ->
                    WeatherCardInfo(
                        cityWeatherInfo = cityWeather,
                        onTapCity = onTapCity
                    )
                }
            } else {
                item {
                    NoWeatherCardInfo(onTapNavigateBack = onTapNavigateBack)
                }
            }
        }
    }
}

