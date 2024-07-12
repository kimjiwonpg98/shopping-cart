package kr.co.shoppingcart.cart.api.template.dto.`in`

import jakarta.validation.constraints.NotBlank

data class UpdateTemplateSharedRequestParamsDto(
    @field:NotBlank
    val isShared: Boolean
)
