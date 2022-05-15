package com.joaquim_gomes_wit_challenge.data.commom

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.util.*

const val UTCISOFormatLocalDateTime = "yyyy-MM-dd'T'HH:mm:ss.SSS"
const val UTCISOFormatToGetLocalDateTime = "T00:00"

class GetCalendar {

    private val timeZone = TimeZone.currentSystemDefault()
    private val currentMoment = Clock.System.now()

    fun getDate(date: LocalDateTime? = null): String {
        return date?.toString()
            ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
            ?.dropLast(6)
            ?: currentMoment.toLocalDateTime(timeZone).date.toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
    }
}