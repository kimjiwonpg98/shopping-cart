package kr.co.shoppingcart.cart.api.template.dto.request

import jakarta.validation.constraints.NotBlank

data class CreateTemplateRequestBodyDto(
    @field:NotBlank(message = "Name must not be blank")
    val name: String,
)
