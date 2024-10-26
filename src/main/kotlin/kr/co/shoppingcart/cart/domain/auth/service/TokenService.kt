package kr.co.shoppingcart.cart.domain.auth.service

interface TokenService {
    fun createAccessToken(
        userId: Long,
        provider: String,
    ): String

    fun createRefreshToken(
        userId: Long,
        provider: String,
    ): String

    fun createTmpToken(identificationValue: String): String
}
