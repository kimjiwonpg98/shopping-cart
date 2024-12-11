package kr.co.shoppingcart.cart.domain.auth.service

import kr.co.shoppingcart.cart.auth.JwtPayload
import kr.co.shoppingcart.cart.auth.JwtProvider
import org.springframework.stereotype.Component

@Component
class UpdateTokensService(
    private val jwtProvider: JwtProvider,
) {
    fun verifyRefreshToken(refreshToken: String): JwtPayload = jwtProvider.verifyToken(refreshToken)
}
