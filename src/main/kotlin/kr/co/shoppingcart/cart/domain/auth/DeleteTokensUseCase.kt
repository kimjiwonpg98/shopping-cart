package kr.co.shoppingcart.cart.domain.auth

import kr.co.shoppingcart.cart.domain.auth.service.TokenService
import org.springframework.stereotype.Service

@Service
class DeleteTokensUseCase(
    private val tokenService: TokenService,
) {
    fun deleteCacheTokenByIdentifier(identifier: String) {
        tokenService.deleteCacheByIdentifier(identifier)
    }
}
