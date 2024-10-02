package kr.co.shoppingcart.cart.api.user.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull

data class LoginRequestBodyDto(
    @field:Email(message = "Doesn't fit the email format.")
    val email: String,
    @field:NotNull
    val loginType: String,
    val gender: String?,
    val birth: String?,
)
