package kr.co.shoppingcart.cart.domain.auth.service

interface TokenService {
    fun createAccessToken(
        userId: Long,
        loginType: String,
    ): String

    fun createRefreshToken(
        userId: Long,
        loginType: String,
    ): String

    fun createTmpToken(identificationValue: String): String
}
