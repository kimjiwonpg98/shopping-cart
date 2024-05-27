package kr.co.shoppingcart.cart.api.user.dto

data class LoginResponseBodyDto(
    val accessToken: String,
    val refreshToken: String,
)