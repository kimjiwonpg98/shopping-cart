package kr.co.shoppingcart.cart.domain.auth.vo

import kr.co.shoppingcart.cart.domain.auth.service.TokenService
import kr.co.shoppingcart.cart.domain.auth.vo.tokens.Tokens
import kr.co.shoppingcart.cart.domain.user.command.LoginCommand
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.springframework.stereotype.Service

@Service
class CreateTokensUseCase (
    private val tokenService: TokenService
) {
    fun createTokensByUser(loginCommand: LoginCommand, user: User): Tokens {
        val accessToken = tokenService.createAccessToken(user.userId.id, loginCommand.loginType)
        val refreshToken = tokenService.createRefreshToken(user.userId.id, loginCommand.loginType)

        return Tokens.toDomain(accessToken, refreshToken)
    }
}