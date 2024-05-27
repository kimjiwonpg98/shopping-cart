package kr.co.shoppingcart.cart.domain.auth

import kr.co.shoppingcart.cart.domain.auth.vo.expiration.TokenExpiration

interface TokenExpirationRepository {
    fun getByName(name: String): TokenExpiration?
}