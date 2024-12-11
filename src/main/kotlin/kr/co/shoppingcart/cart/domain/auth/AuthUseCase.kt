package kr.co.shoppingcart.cart.domain.auth

import kr.co.shoppingcart.cart.domain.auth.service.TokenService
import kr.co.shoppingcart.cart.domain.auth.service.UpdateTokensService
import kr.co.shoppingcart.cart.domain.auth.vo.tokens.Tokens
import org.springframework.stereotype.Service

@Service
class AuthUseCase(
    private val updateTokenService: UpdateTokensService,
    private val tokenService: TokenService,
) {
    fun updateTokenByRefreshToken(oldRefreshToken: String): Tokens {
        val payload = updateTokenService.verifyRefreshToken(oldRefreshToken)

        val accessToken =
            tokenService.createAccessToken(
                payload.identificationValue.toLong(),
                payload.provider,
            )

        val refreshToken =
            tokenService.createRefreshToken(
                payload.identificationValue.toLong(),
                payload.provider,
            )

        return Tokens.toDomain(
            accessToken = accessToken,
            refreshToken = refreshToken,
        )
    }
}
