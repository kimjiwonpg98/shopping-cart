package kr.co.shoppingcart.cart.utils

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Date

object DateUtil {
    fun convertZoneDateTimeToDate(time: ZonedDateTime): Date = Date.from(time.toInstant())

    fun convertZonedDateTimeToSeoulTime(time: ZonedDateTime): ZonedDateTime =
        time.toInstant().atZone(ZoneId.of("Asia/Seoul"))

    fun convertLocalDateTimeToSeoulTime(time: LocalDateTime): ZonedDateTime = time.atZone(ZoneId.of("Asia/Seoul"))
}
