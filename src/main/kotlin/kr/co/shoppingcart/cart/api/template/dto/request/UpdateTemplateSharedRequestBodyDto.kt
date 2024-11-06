package kr.co.shoppingcart.cart.api.template.dto.request

import jakarta.validation.constraints.NotBlank

data class UpdateTemplateSharedRequestBodyDto(
    @field:NotBlank
    val isShared: Boolean,
)
