package com.joaquim_gomes_wit_challenge.views.ui.compose.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.data.commom.extensions.getSystemMetric
import com.joaquim_gomes_wit_challenge.data.model.googleMaps.GoogleMapsObjects.MAPS_ZOOM_STREETS
import com.joaquim_gomes_wit_challenge.data.model.weather.ScreenWeatherInfo
import com.joaquim_gomes_wit_challenge.views.ui.compose.CityDetailsComposeFragment
import com.joaquim_gomes_wit_challenge.views.ui.compose.components.SetWeatherCardRowInfo

@Composable
fun CityDetaisComposeScreen(
    selectedCityInfo: ScreenWeatherInfo?,
    fragmentContext: CityDetailsComposeFragment
) {
    AnimatedVisibility(
        visible = true,
        enter = expandVertically() + fadeIn(),
        exit = shrinkVertically() + fadeOut()
    ) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
            RenderCityDetailsMapContent(selectedCityInfo)
            RenderCityDetailsBodyContent(
                selectedCityInfo,
                fragmentContext
            )
        }
    }
}

@Composable
fun RenderCityDetailsMapContent(selectedCityInfo: ScreenWeatherInfo?) {
    val cityLatLng = LatLng(selectedCityInfo?.lat ?: 0.0, selectedCityInfo?.lng ?: 0.0)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cityLatLng, MAPS_ZOOM_STREETS)
    }

    Box(Modifier.fillMaxWidth()) {
        GoogleMap(
            modifier = Modifier.fillMaxWidth().height(212.dp),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = cityLatLng),
                title = selectedCityInfo?.nameCity ?: ""
            )
        }
    }
}

@Composable
fun RenderCityDetailsBodyContent(
    selectedCityInfo: ScreenWeatherInfo?,
    fragmentContext: CityDetailsComposeFragment
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SetWeatherCardRowInfo(
                selectedCityInfo?.nameCity ?: "",
                true,
                R.drawable.ic_baseline_location_city_24,
                stringResource(R.string.drawable_name_city_description)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start
        ) {
            SetWeatherCardRowInfo(
                stringResource(
                    R.string.general_text_place_holder_description_weather_temp
                ).replace("#weather_temp", selectedCityInfo?.temperatureActual ?: ""),
                false,
                R.drawable.ic_baseline_adjust_24,
                stringResource(R.string.drawable_temp_city_description)
            )

            SetWeatherCardRowInfo(
                stringResource(R.string.details_fragment_min_temp).replace(
                    "#minTemp",
                    "${selectedCityInfo?.temperatureMin}"
                ),
                false,
                R.drawable.ic_baseline_remove_circle_outline_24,
                stringResource(R.string.drawable_min_temp_city_description)
            )

            SetWeatherCardRowInfo(
                stringResource(R.string.details_fragment_max_temp).replace(
                    "#maxTemp",
                    "${selectedCityInfo?.temperatureMax}"
                ),
                false,
                R.drawable.ic_baseline_add_circle_24,
                stringResource(R.string.drawable_max_temp_city_description)
            )

            SetWeatherCardRowInfo(
                stringResource(R.string.details_fragment_humidity).replace(
                    "#humidity",
                    "${selectedCityInfo?.humidity}"
                ),
                false,
                R.drawable.ic_baseline_invert_colors_24,
                stringResource(R.string.drawable_weather_description_city_humidity)
            )

            SetWeatherCardRowInfo(
                selectedCityInfo?.descriptionWeather ?: "",
                false,
                R.drawable.ic_baseline_info_24,
                stringResource(R.string.drawable_weather_description_city_description)
            )

            SetWeatherCardRowInfo(
                selectedCityInfo?.date ?: "",
                false,
                R.drawable.ic_baseline_calendar_month_24,
                stringResource(R.string.drawable_date_city_description)
            )

            SetWeatherCardRowInfo(
                stringResource(R.string.details_fragment_pressure).replace(
                    "#pressure",
                    "${selectedCityInfo?.pressure}"
                ),
                false,
                R.drawable.ic_baseline_compress_24,
                stringResource(R.string.drawable_air_pressure_city_description)
            )

            SetWeatherCardRowInfo(
                stringResource(R.string.details_fragment_visibility).replace(
                    "#visibility",
                    "${selectedCityInfo?.visibility}"
                ),
                false,
                R.drawable.ic_baseline_remove_red_eye_24,
                stringResource(R.string.drawable_visibility_city_description)
            )

            SetWeatherCardRowInfo(
                stringResource(R.string.details_fragment_wind_speed).replace(
                    "#windSpeed",
                    "${selectedCityInfo?.windSpeed}"
                ).replace("#metric", fragmentContext.getSystemMetric()),
                false,
                R.drawable.ic_baseline_double_arrow_24,
                stringResource(R.string.drawable_wind_velocity_description)
            )
        }

    }
}
