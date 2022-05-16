package com.joaquim_gomes_wit_challenge.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.databinding.DataBindingUtil
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.data.commom.SharedPrefs
import com.joaquim_gomes_wit_challenge.data.commom.extensions.getSystemLocale
import com.joaquim_gomes_wit_challenge.data.model.weather.WeatherInfoObjects.WEATHER_API_CALL_LANG
import com.joaquim_gomes_wit_challenge.data.model.weather.WeatherInfoObjects.WEATHER_API_CALL_UNITS
import com.joaquim_gomes_wit_challenge.databinding.ActivityMainBinding
import com.joaquim_gomes_wit_challenge.views.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val homeViewModel: HomeViewModel by viewModel()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_main
        )

        getApiKeys()
        initWeatherInfoApiObjects()

        setContentView(binding.root)

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun initWeatherInfoApiObjects() {
        val systemLocaleLang = this.getSystemLocale()

        WEATHER_API_CALL_LANG = systemLocaleLang.dropLast(3).lowercase()

        WEATHER_API_CALL_UNITS = if (systemLocaleLang == "en_GB" || systemLocaleLang == "en_US") {
            "imperial"
        } else {
            "metric"
        }
    }

    private fun getApiKeys() {
        homeViewModel.getApiKeys()
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedPrefs.clearSharedPreferences()
    }
}