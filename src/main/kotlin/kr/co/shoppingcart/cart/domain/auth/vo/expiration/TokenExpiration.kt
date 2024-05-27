package kr.co.shoppingcart.cart.domain.auth.vo.expiration

data class TokenExpiration(
    val tokenName: TokenName,
    val tokenTTL: TokenTTL,
    val refreshTokenTTL: RefreshTokenTTL
)