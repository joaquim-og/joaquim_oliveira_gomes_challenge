package com.joaquim_gomes_wit_challenge.views

import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.joaquim_gomes_wit_challenge.MyApplication
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.data.commom.SharedPrefs
import com.joaquim_gomes_wit_challenge.data.network.weather_api.WeatherInfoObjects
import com.joaquim_gomes_wit_challenge.data.network.weather_api.WeatherInfoObjects.WEATHER_API_CALL_LANG
import com.joaquim_gomes_wit_challenge.data.network.weather_api.WeatherInfoObjects.WEATHER_API_CALL_UNITS
import com.joaquim_gomes_wit_challenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initWeatherInfoApiObjects()
        getApiKeys()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun initWeatherInfoApiObjects() {
        val systemLocaleLang = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            this.resources.configuration.locales[0].toString().lowercase()
        } else {
            this.resources.configuration.locale.toString().lowercase()
        }

        WEATHER_API_CALL_LANG = systemLocaleLang

        WEATHER_API_CALL_UNITS = if (systemLocaleLang == "en_GB" || systemLocaleLang == "en_US") {
            "imperial"
        } else {
            "metric"
        }
    }

    private fun getApiKeys() {
//        mainActivityViewModel.getApiKeys()
    }

    override fun onDestroy() {
        super.onDestroy()
        SharedPrefs.clearSharedPreferences()
    }
}