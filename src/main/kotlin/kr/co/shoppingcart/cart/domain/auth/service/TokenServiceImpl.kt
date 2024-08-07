package kr.co.shoppingcart.cart.domain.auth.service

import kr.co.shoppingcart.cart.auth.JwtPayloadDto
import kr.co.shoppingcart.cart.auth.JwtProvider
import kr.co.shoppingcart.cart.database.mysql.tokenExpiration.TokenExpirationRepositoryAdapter
import kr.co.shoppingcart.cart.domain.auth.vo.expiration.TokenExpiration
import org.apache.coyote.BadRequestException
import org.springframework.stereotype.Component
import java.time.ZonedDateTime

@Component
class TokenServiceImpl(
    private val tokenExpirationRepositoryAdapter: TokenExpirationRepositoryAdapter,
    private val jwtProvider: JwtProvider,
) : TokenService {
    override fun createAccessToken(
        userId: Long,
        loginType: String,
    ): String {
        val tokenInfo = getTokenExpByType(loginType) ?: throw BadRequestException()

        val claims = mapOf("scope" to "MASTER")

        val jwtPayload =
            JwtPayloadDto(
                claims = claims,
                identificationValue = userId.toString(),
                now = ZonedDateTime.now(),
                expiredTimestamp = tokenInfo.tokenTTL.ttl,
            )

        return jwtProvider.createJwt(jwtPayload)
    }

    override fun createRefreshToken(
        userId: Long,
        loginType: String,
    ): String {
        val tokenInfo = getTokenExpByType(loginType) ?: throw BadRequestException()

        val claims = mapOf("scope" to "REFRESH")

        val jwtPayload =
            JwtPayloadDto(
                claims = claims,
                identificationValue = userId.toString(),
                now = ZonedDateTime.now(),
                expiredTimestamp = tokenInfo.refreshTokenTTL.refreshTokenTtl,
            )

        return jwtProvider.createJwt(jwtPayload)
    }

    override fun createTmpToken(identificationValue: String): String {
        TODO("Not yet implemented")
    }

    private fun getTokenExpByType(loginType: String): TokenExpiration? =
        tokenExpirationRepositoryAdapter.getByName(
            loginType,
        )
}
