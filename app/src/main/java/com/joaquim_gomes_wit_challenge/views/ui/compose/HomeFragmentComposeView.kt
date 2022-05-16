package com.joaquim_gomes_wit_challenge.views.ui.compose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.data.commom.extensions.navigateSafe
import com.joaquim_gomes_wit_challenge.views.ui.compose.screens.HomeFragmentComposeScreen
import com.joaquim_gomes_wit_challenge.views.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class HomeFragmentComposeView : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    HomeFragmentComposeScreen(
                        listWeatherInfo = homeViewModel.actualWeatherInfo.value ?: listOf(),
                        onTapCity = { cityWeatherInfo ->
                            homeViewModel.setSelectedCity(cityWeatherInfo)
                            navigateToSelectedCity()
                        },
                        onTapNavigateBack = ::navigateBack
                    )
                }
            }
        }
    }

    private fun navigateToSelectedCity() {
        navigateSafe(R.id.action_homeFragmentComposeView_to_cityDetailsComposeFragment)
    }

    private fun navigateBack() {
        navigateSafe(R.id.action_homeFragmentComposeView_to_nav_home)
    }

}