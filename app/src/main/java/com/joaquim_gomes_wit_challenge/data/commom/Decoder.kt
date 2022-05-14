package com.joaquim_gomes_wit_challenge.data.commom

class Decoder {

    fun decoder(key: String): String =
        android.util.Base64.decode(key, android.util.Base64.DEFAULT).decodeToString()

}