package kr.co.shoppingcart.cart.auth

import java.time.ZonedDateTime

data class JwtPayloadDto(
    val claims: Map<String, Any>?,
    val expiredTimestamp: Long,
    val identificationValue: String,
    val now: ZonedDateTime = ZonedDateTime.now(),
)
