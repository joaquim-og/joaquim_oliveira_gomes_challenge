package com.joaquim_gomes_wit_challenge.views.ui.xml

import android.Manifest
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.anyDenied
import com.fondesa.kpermissions.anyPermanentlyDenied
import com.fondesa.kpermissions.extension.liveData
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.joaquim_gomes_wit_challenge.R
import com.joaquim_gomes_wit_challenge.data.commom.SetToastMessage
import com.joaquim_gomes_wit_challenge.data.commom.extensions.navigateSafe
import com.joaquim_gomes_wit_challenge.data.commom.network.VerifyNetwork
import com.joaquim_gomes_wit_challenge.databinding.FragmentRequestLocationBottomDialogBinding
import org.koin.android.ext.android.inject


class FragmentRequestLocationBottomDialog : BottomSheetDialogFragment() {

    private val toastMessage: SetToastMessage by inject()
    private val verifyNetwork: VerifyNetwork by inject()

    private var _binding: FragmentRequestLocationBottomDialogBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_request_location_bottom_dialog,
                container,
                false
            )

        setupViews()

        return binding.root
    }

    private fun setupViews() {

        with(binding) {
            fragmentRequestLocationDialogButton.setOnClickListener {

                with(verifyNetwork) {

                    performActionIfConnected {
                        requestUserPermission()
                    }

                    performActionIfIsNOTConnected {
                        toastMessage.setToastMessage(R.string.no_network_error)
                    }
                }

            }
        }

    }

    private fun requestUserPermission() {

        val requestPermissions = permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).build()

        requestPermissions.liveData().observe(viewLifecycleOwner, Observer { result ->

            if (result.allGranted()) {

                navigateToHomeFragment()

            } else if (result.anyDenied() || result.anyPermanentlyDenied()) {

                toastMessage.setToastMessage(R.string.access_fine_location_permissions_denied)
            }
        })

        requestPermissions.send()
    }

    private fun navigateToHomeFragment() {
        navigateSafe(R.id.action_fragmentRequestLocationBottomDialog_to_nav_home)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        dismiss()
    }

}