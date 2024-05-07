package kr.co.shoppingcart.cart.api.cart.dto

import jakarta.validation.constraints.NotBlank

data class CreateCartRequestBodyDto (
    @field:NotBlank(message = "Name must not be blank")
    val name: String
)