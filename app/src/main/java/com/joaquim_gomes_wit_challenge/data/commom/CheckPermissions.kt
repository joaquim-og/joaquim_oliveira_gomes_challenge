package com.joaquim_gomes_wit_challenge.data.commom

import androidx.core.content.ContextCompat
import com.joaquim_gomes_wit_challenge.MyApplication.Companion.globalContext

class CheckPermissions {

    fun checkPermission(permissionToCheck: String): Boolean {

        val checkAskedPermission = ContextCompat.checkSelfPermission(
            globalContext,
            permissionToCheck
        )

        return when (checkAskedPermission) {
            0 -> true
            else -> false
        }

    }

}