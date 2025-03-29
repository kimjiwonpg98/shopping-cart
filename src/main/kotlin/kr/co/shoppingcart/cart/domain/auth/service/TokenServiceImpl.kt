package kr.co.shoppingcart.cart.domain.auth.service

import kr.co.shoppingcart.cart.auth.JwtPayloadDto
import kr.co.shoppingcart.cart.auth.JwtProvider
import kr.co.shoppingcart.cart.core.tokenexpiration.application.port.inpout.GetTokenExpiration
import kr.co.shoppingcart.cart.core.tokenexpiration.application.port.inpout.GetTokenExpirationCommand
import kr.co.shoppingcart.cart.core.tokenexpiration.domain.TokenExpiration
import kr.co.shoppingcart.cart.domain.auth.vo.tokens.Tokens
import org.apache.coyote.BadRequestException
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class TokenServiceImpl(
    private val getTokenExpiration: GetTokenExpiration,
    private val jwtProvider: JwtProvider,
) : TokenService {
    override fun createAccessToken(
        userId: Long,
        provider: String,
    ): String {
        val tokenInfo = getTokenExpByType(provider) ?: throw BadRequestException()

        val claims = mapOf("scope" to "MASTER", "provider" to provider)

        val jwtPayload =
            JwtPayloadDto(
                claims = claims,
                identificationValue = userId.toString(),
                now = ZonedDateTime.now(),
                expiredTimestamp = tokenInfo.ttl,
            )

        return jwtProvider.createJwt(jwtPayload)
    }

    override fun createRefreshToken(
        userId: Long,
        provider: String,
    ): String {
        val tokenInfo = getTokenExpByType(provider) ?: throw BadRequestException()

        val claims = mapOf("scope" to "REFRESH", "provider" to provider)

        val jwtPayload =
            JwtPayloadDto(
                claims = claims,
                identificationValue = userId.toString(),
                now = ZonedDateTime.now(),
                expiredTimestamp = tokenInfo.refreshTTL,
            )

        return jwtProvider.createJwt(jwtPayload)
    }

    @Cacheable(
        value = ["token"],
        key = "#identifier",
    )
    override fun settingCacheTokenByOauth2(
        identifier: String,
        accessToken: String,
        refreshToken: String,
    ): Tokens =
        Tokens.toDomain(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )

    @Cacheable(
        value = ["token"],
        key = "#identifier",
        unless = "#result == null",
    )
    override fun getCacheTokenByOauth2(identifier: String): Tokens? = null

    @CacheEvict(
        value = ["token"],
        key = "#identifier",
    )
    override fun deleteCacheByIdentifier(identifier: String) {
    }

    private fun getTokenExpByType(provider: String): TokenExpiration? =
        getTokenExpiration.getByName(
            GetTokenExpirationCommand(
                name = provider,
            ),
        )
}
