package kr.co.shoppingcart.cart.domain.auth.service

import kr.co.shoppingcart.cart.domain.auth.vo.tokens.Tokens

interface TokenService {
    fun createAccessToken(
        userId: Long,
        provider: String,
    ): String

    fun createRefreshToken(
        userId: Long,
        provider: String,
    ): String

    fun settingCacheTokenByOauth2(
        identifier: String,
        accessToken: String,
        refreshToken: String,
    ): Tokens

    fun getCacheTokenByOauth2(identifier: String): Tokens?

    fun deleteCacheByIdentifier(identifier: String)
}
