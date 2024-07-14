package kr.co.shoppingcart.cart.api.user.dto.response

data class LoginResponseBodyDto(
    val accessToken: String,
    val refreshToken: String,
)
