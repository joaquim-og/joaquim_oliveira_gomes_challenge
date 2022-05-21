package com.joaquim_gomes_wit_challenge.views.ui.xml

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.data.commom.CheckPermissions
import com.joaquim_gomes_wit_challenge.data.commom.extensions.navigateSafe
import com.joaquim_gomes_wit_challenge.databinding.FragmentHomeBinding
import com.joaquim_gomes_wit_challenge.databinding.FragmentRequestPermissionBinding
import org.koin.android.ext.android.inject

class RequestPermissionFragment : Fragment() {

    private val checkPermissions: CheckPermissions by inject()

    private var _binding: FragmentRequestPermissionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRequestPermissionBinding.inflate(inflater, container, false)
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
            navigateToHomeFragment()
        }
    }

    private fun setupViews() {
        with(binding) {

            fragmentRequestPermissionsGetLocationsPermissions.setOnClickListener {
                if (checkPermissions.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) && checkPermissions.checkPermission(
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                ) {
                    navigateToHomeFragment()
                } else {
                    navigateToRequestUserLocationPermissionDialog()
                }

            }
        }
    }

    private fun navigateToRequestUserLocationPermissionDialog() {
        navigateSafe(R.id.action_requestPermissionFragment_to_fragmentRequestLocationBottomDialog)
    }

    private fun navigateToHomeFragment() {
        navigateSafe(R.id.action_requestPermissionFragment_to_nav_home)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}