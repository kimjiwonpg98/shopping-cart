package kr.co.shoppingcart.cart.utils

import java.time.ZonedDateTime
import java.util.*

object DateUtil {
    fun convertZoneDateTimeToDate(time: ZonedDateTime): Date =
        Date.from(time.toInstant())
}