package kr.co.shoppingcart.cart.api.template.dto.`in`

import jakarta.validation.constraints.NotBlank

data class GetTemplateByIdRequestParamsDto (
    @field:NotBlank
    val id: Long
)