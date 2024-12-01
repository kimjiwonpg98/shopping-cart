package kr.co.shoppingcart.cart.domain.auth

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.auth.service.TokenService
import kr.co.shoppingcart.cart.domain.auth.vo.tokens.Tokens
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.springframework.stereotype.Service

@Service
class CreateTokensUseCase(
    private val tokenService: TokenService,
) {
    fun createTokensByUser(user: User): Tokens {
        val accessToken = tokenService.createAccessToken(user.userId.id, user.provider.provider.name)
        val refreshToken = tokenService.createRefreshToken(user.userId.id, user.provider.provider.name)

        return Tokens.toDomain(accessToken, refreshToken)
    }

    fun getTokensByIdentifier(identifier: String): Tokens =
        tokenService.getCacheTokenByOauth2(
            identifier,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_401_001)
}
