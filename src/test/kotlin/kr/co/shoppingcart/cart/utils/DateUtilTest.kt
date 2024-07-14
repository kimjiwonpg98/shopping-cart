package kr.co.shoppingcart.cart.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

@DisplayName("Date Util 테스트")
class DateUtilTest {
    @DisplayName("ZoneDateTime 시간 -> Date 형식으로 변경")
    @Test
    fun convertZoneDateTimeToDateTest() {
        val testDate: Date = DateUtil.convertZoneDateTimeToDate(zoneDateTime)
        assertEquals(testDate, dateTime)
    }

    @DisplayName("ZonedDateTime -> 서울 리전으로 포맷팅")
    @Test
    fun convertZonedDateTimeToSeoulTimeTest() {
        val testDate: ZonedDateTime = DateUtil.convertZonedDateTimeToSeoulTime(utcDateTime)
        assertEquals(testDate, zoneDateTime)
    }

    companion object {
        private val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss")
        private val zoneDateTime =
            ZonedDateTime.parse(
                "2024.05.18 16:00:00",
                dateTimeFormatter.withZone(java.time.ZoneId.systemDefault()),
            )
        private val dateTime = SimpleDateFormat("yyyy.MM.dd HH:mm:ss").parse("2024.05.18 16:00:00")
        private val utcDateTime =
            ZonedDateTime.parse(
                "2024.05.18 07:00:00",
                dateTimeFormatter.withZone(java.time.ZoneId.of("UTC")),
            )
    }
}
