package com.joaquim_gomes_wit_challenge.data.commom.extensions

import android.app.Activity
import android.os.Build

fun Activity.getSystemLocale(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.resources.configuration.locales[0].toString()
    } else {
        this.resources.configuration.locale.toString()
    }
}