package com.joaquim_gomes_wit_challenge.views.ui.compose

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.data.commom.extensions.navigateSafe
import com.joaquim_gomes_wit_challenge.views.ui.compose.screens.CityDetaisComposeScreen
import com.joaquim_gomes_wit_challenge.views.viewModel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class CityDetailsComposeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            navigateBack()
        }

        return ComposeView(requireContext()).apply {
            setContent {
                MaterialTheme {
                    CityDetaisComposeScreen(
                        selectedCityInfo = homeViewModel.selectedCityWeatherData.value,
                        this@CityDetailsComposeFragment
                    )
                }
            }
        }
    }

    private fun navigateBack() {
        navigateSafe(R.id.action_cityDetailsComposeFragment_to_homeFragmentComposeView)
    }

}