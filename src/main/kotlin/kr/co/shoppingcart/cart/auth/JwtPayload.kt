package kr.co.shoppingcart.cart.auth

import java.time.ZonedDateTime

data class JwtPayload (
    val email: String,
    val expiredTimestamp: Long,
    val now: ZonedDateTime = ZonedDateTime.now()
)