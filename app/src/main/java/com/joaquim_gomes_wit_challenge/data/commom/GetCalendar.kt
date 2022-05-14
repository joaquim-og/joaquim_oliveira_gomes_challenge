package com.joaquim_gomes_wit_challenge.data.commom

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.util.*

const val UTCISOFormatLocalDateTime = "yyyy-MM-dd'T'HH:mm:ss.SSS"
const val UTCISOFormatToGetLocalDateTime = "T00:00"

class GetCalendar {

    private val timeZone = TimeZone.currentSystemDefault()
    private val currentMoment = Clock.System.now()

    fun getMonth(date: LocalDateTime? = null): String {
        return if (date != null) {
            date.month.toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        } else {
            currentMoment.toLocalDateTime(timeZone).month.toString()
                .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        }
    }

    fun getDayNumber(date: LocalDateTime? = null): String {
        return if (date != null) {
            date.dayOfMonth.toString()
        } else {
            val actualDay = currentMoment.toLocalDateTime(timeZone).dayOfMonth.toString()
            if(actualDay.length < 2){
                "0$actualDay"
            } else {
                actualDay
            }
        }
    }

    fun getYearNumber(): String {
        return currentMoment.toLocalDateTime(timeZone).year.toString()
    }

    fun getMonthNumber(): String {
        return currentMoment.toLocalDateTime(timeZone).monthNumber.toString()
    }

}