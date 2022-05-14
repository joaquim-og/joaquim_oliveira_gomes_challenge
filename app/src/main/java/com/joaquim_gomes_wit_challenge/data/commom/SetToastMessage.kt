package com.joaquim_gomes_wit_challenge.data.commom

import android.content.Context
import android.widget.Toast
import com.joaquim_gomes_wit_challenge.MyApplication.Companion.globalContext

class SetToastMessage {

        private val context = globalContext

        fun setToastMessage(text: Int) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }

        fun setToastMessage(text: String) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }

        fun setToastMessageLocalContext(context: Context?, text: Int) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }

}