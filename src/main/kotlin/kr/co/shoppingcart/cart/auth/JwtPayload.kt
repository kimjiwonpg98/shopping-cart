package kr.co.shoppingcart.cart.auth

data class JwtPayload(
    val email: String? = null,
    val provider: String,
    val identificationValue: String,
)
