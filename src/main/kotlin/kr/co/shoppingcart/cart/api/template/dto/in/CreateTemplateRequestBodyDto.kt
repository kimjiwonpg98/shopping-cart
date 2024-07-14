package kr.co.shoppingcart.cart.api.template.dto.`in`

import jakarta.validation.constraints.NotBlank

data class CreateTemplateRequestBodyDto (
    @field:NotBlank(message = "Name must not be blank")
    val name: String
)