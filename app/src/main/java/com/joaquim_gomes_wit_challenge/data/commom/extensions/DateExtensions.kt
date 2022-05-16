package com.joaquim_gomes_wit_challenge.data.commom.extensions

import java.text.SimpleDateFormat
import java.util.*

const val format = "EEE, MMMM d"

fun Int.toDateTime(): String {
    return try {
        val sdf = SimpleDateFormat(format)
        val netDate = Date(this.toLong() * 1000)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}