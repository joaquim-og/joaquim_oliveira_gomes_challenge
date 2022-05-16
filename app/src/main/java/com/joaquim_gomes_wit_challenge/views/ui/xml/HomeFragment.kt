package com.joaquim_gomes_wit_challenge.views.ui.xml

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.LocationServices
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.data.commom.CheckPermissions
import com.joaquim_gomes_wit_challenge.data.commom.SetToastMessage
import com.joaquim_gomes_wit_challenge.data.commom.extensions.hide
import com.joaquim_gomes_wit_challenge.data.commom.extensions.navigateSafe
import com.joaquim_gomes_wit_challenge.data.commom.extensions.show
import com.joaquim_gomes_wit_challenge.data.commom.network.VerifyNetwork
import com.joaquim_gomes_wit_challenge.data.model.weather.ScreenWeatherInfo
import com.joaquim_gomes_wit_challenge.databinding.FragmentHomeBinding
import com.joaquim_gomes_wit_challenge.views.ui.xml.adapter.HomeFragmentAdapter
import com.joaquim_gomes_wit_challenge.views.viewModel.HomeViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by sharedViewModel()

    private val toastMessage: SetToastMessage by inject()
    private val verifyNetwork: VerifyNetwork by inject()
    private val checkPermissions: CheckPermissions by inject()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkPermissions()
        setupViews()

    }

    private fun checkPermissions() {
        if (checkPermissions.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) && checkPermissions.checkPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        ) {
            getWeatherData()
            hideRequestPermissionsUiElements()
            observeWeatherInfo()
        }
    }

    private fun setupViews() {

        with(binding) {

            fragmentHomeEventRecyclerView.layoutManager = LinearLayoutManager(requireContext())

            fragmentHomeButtonGetLocationsPermissions.setOnClickListener {

                with(verifyNetwork) {
                    performActionIfConnected {
                        if (checkPermissions.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) && checkPermissions.checkPermission(
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        ) {
                            getWeatherData()
                            hideRequestPermissionsUiElements()
                            observeWeatherInfo()
                        } else {
                            navigateToRequestUserLocationPermissionDialog()
                        }
                    }

                    performActionIfIsNOTConnected {
                        toastMessage.setToastMessage(R.string.no_network_error)
                    }
                }
            }

            fragmentHomeButtonGoToComposeUi.setOnClickListener {
                navigateToComposeUi()
            }
        }
    }

    private fun getWeatherData() {
        val locationServices = LocationServices.getFusedLocationProviderClient(requireActivity())
        homeViewModel.getWeatherData(locationServices)
    }

    private fun observeWeatherInfo() {
        binding.fragmentHomeLoading.show()

        homeViewModel.actualWeatherInfo.observe(
            viewLifecycleOwner,
            Observer { eventsWeatherDetails ->
                eventsWeatherDetails?.let {
                    setRecyclerData(it)
                } ?: toastMessage.setToastMessage(R.string.error_get_api_data)
            })
    }

    private fun setRecyclerData(listWeatherInfo: List<ScreenWeatherInfo?>) {

        val weatherListData = mutableListOf<ScreenWeatherInfo>()

        listWeatherInfo.forEach {
            it?.let { localWeatherData ->
                weatherListData.add(localWeatherData)
            }
        }

        val weatherAdapter = HomeFragmentAdapter(
            weatherListData, homeViewModel, onClick = {
                homeViewModel.setSelectedCity(it)
                navigateToSelectedCity()
            }
        )

        if (weatherListData.isNotEmpty()) {
            with(binding) {
                fragmentHomeLoading.hide()
                fragmentHomeEventRecyclerView.adapter = weatherAdapter
                fragmentHomeButtonGoToComposeUi.show()
                fragmentHomeEventRecyclerView.show()
                weatherAdapter.notifyDataSetChanged()
            }
        }

    }

    private fun navigateToSelectedCity() {
        navigateSafe(R.id.action_nav_home_to_cityDetailsFragment)
    }


    private fun navigateToRequestUserLocationPermissionDialog() {
        navigateSafe(R.id.action_nav_home_to_fragmentRequestLocationBottomDialog)
    }

    private fun navigateToComposeUi() {
        navigateSafe(R.id.action_nav_home_to_homeFragmentComposeView)
    }

    private fun hideRequestPermissionsUiElements() {
        with(binding) {
            fragmentHomeTextPermissionsFirst.hide()
            fragmentHomeTextPermissionsSecond.hide()
            fragmentHomeButtonGetLocationsPermissions.hide()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}