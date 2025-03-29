package kr.co.shoppingcart.cart.core.tokenexpiration.application

import kr.co.shoppingcart.cart.core.tokenexpiration.application.port.inpout.GetTokenExpiration
import kr.co.shoppingcart.cart.core.tokenexpiration.application.port.inpout.GetTokenExpirationCommand
import kr.co.shoppingcart.cart.core.tokenexpiration.application.port.output.TokenExpirationRepository
import kr.co.shoppingcart.cart.core.tokenexpiration.domain.TokenExpiration
import org.springframework.stereotype.Service

@Service
class GetTokenExpirationService(
    private val tokenExpirationRepository: TokenExpirationRepository<TokenExpiration, Long>,
) : GetTokenExpiration {
    override fun getByName(command: GetTokenExpirationCommand): TokenExpiration? =
        tokenExpirationRepository.getByName(command.name)
}
