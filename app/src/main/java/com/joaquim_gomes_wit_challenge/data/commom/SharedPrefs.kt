package com.joaquim_gomes_wit_challenge.data.commom

import android.content.Context
import android.content.SharedPreferences
import com.joaquim_gomes_wit_challenge.MyApplication.Companion.globalContext

private const val PREFS_KEY = "WIT_CHALLENGE_PREFS"

open class SharedPrefs {

    companion object {

        private var sharedPrefs: SharedPreferences? =
            globalContext.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

        fun getPrefs(context: Context): SharedPreferences {

            if (sharedPrefs == null) {
                sharedPrefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
            }
            return checkNotNull(sharedPrefs)
        }

        fun setApiKeyData(key: String, value: String) {
            getPrefs(globalContext).edit().putString(key, value).apply()
        }

        fun getApiKeyData(key: String?): String {
            return getPrefs(globalContext).getString(key, "") ?: ""
        }

        fun clearSharedPreferences() {
            getPrefs(globalContext).edit().clear().apply()
        }

    }
}