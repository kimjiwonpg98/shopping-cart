package kr.co.shoppingcart.cart.core.tokenexpiration.application.port.inpout

import kr.co.shoppingcart.cart.core.tokenexpiration.domain.TokenExpiration

interface GetTokenExpiration {
    fun getByName(command: GetTokenExpirationCommand): TokenExpiration?
}

data class GetTokenExpirationCommand(
    val name: String,
)
