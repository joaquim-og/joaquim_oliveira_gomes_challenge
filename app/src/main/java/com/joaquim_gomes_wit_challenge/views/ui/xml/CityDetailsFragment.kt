package com.joaquim_gomes_wit_challenge.views.ui.xml

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.joaquim_gomes_wit_challenge.MyApplication.Companion.globalContext
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.data.commom.SetToastMessage
import com.joaquim_gomes_wit_challenge.data.commom.extensions.getSystemMetric
import com.joaquim_gomes_wit_challenge.data.commom.extensions.setMarkerLocation
import com.joaquim_gomes_wit_challenge.data.model.weather.ScreenWeatherInfo
import com.joaquim_gomes_wit_challenge.databinding.FragmentCityDetailsBinding
import com.joaquim_gomes_wit_challenge.views.viewModel.HomeViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class CityDetailsFragment : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()

    private val toastMessage: SetToastMessage by inject()

    private var _binding: FragmentCityDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapGoogle: GoogleMap
    private lateinit var mapView: MapView

    private val callback = OnMapReadyCallback { googleMap ->
        mapGoogle = googleMap

        val mapOptions = googleMap.uiSettings
        mapOptions.isZoomControlsEnabled = true
        mapOptions.isCompassEnabled = true

        homeViewModel.selectedCityWeatherData.observe(
            viewLifecycleOwner,
            Observer { selectedCityWeather ->
                selectedCityWeather?.let {
                    mapGoogle.setMarkerLocation(
                        googleMap,
                        LatLng(selectedCityWeather.lat ?: 0.0, selectedCityWeather.lng ?: 0.0),
                        selectedCityWeather.nameCity
                    )
                } ?: toastMessage.setToastMessage(R.string.error_set_map_marker)
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCityDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.fragmentCityDetailsMapView
        setMapUiConfig(savedInstanceState)
        observeSelectedCityData()
    }

    private fun observeSelectedCityData() {
        homeViewModel.selectedCityWeatherData.observe(
            viewLifecycleOwner,
            Observer { selectedCityWeather ->
                selectedCityWeather?.let {
                    setupViews(it)
                } ?: toastMessage.setToastMessage(R.string.error_get_api_data)
            })
    }

    private fun setupViews(screenWeatherInfo: ScreenWeatherInfo) {

        with(binding) {
            fragmentCityDetailsCityName.text = screenWeatherInfo.nameCity
            fragmentCityDetailsDateTextView.text = screenWeatherInfo.date
            fragmentCityDetailsActualTempTextView.text = getString(R.string.details_fragment_actual_temp).replace("#actualTemp", "${screenWeatherInfo.temperatureActual}")
            fragmentCityDetailsMinTempTextView.text = getString(R.string.details_fragment_min_temp).replace("#minTemp", "${screenWeatherInfo.temperatureMin}")
            fragmentCityDetailsMaxTempTextView.text = getString(R.string.details_fragment_max_temp).replace("#maxTemp", "${screenWeatherInfo.temperatureMax}")
            fragmentCityDetailsHumidityTextView.text = getString(R.string.details_fragment_humidity).replace("#humidity", "${screenWeatherInfo.humidity}")
            fragmentCityDetailsDescriptionTextView.text = screenWeatherInfo.descriptionWeather
            fragmentCityDetailsPressureTextView.text = getString(R.string.details_fragment_pressure).replace("#pressure", "${screenWeatherInfo.pressure}")
            fragmentCityDetailsVisibilityTextView.text = getString(R.string.details_fragment_visibility).replace("#visibility", "${screenWeatherInfo.visibility}")
            fragmentCityDetailsWindSpeedTextView.text = getString(R.string.details_fragment_wind_speed).replace("#windSpeed", "${screenWeatherInfo.windSpeed}").replace("#metric", getSystemMetric())
        }

    }

    private fun setMapUiConfig(savedInstanceState: Bundle?) {
        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        try {
            MapsInitializer.initialize(globalContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        mapView.getMapAsync(callback)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        _binding = null
    }
}