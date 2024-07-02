package kr.co.shoppingcart.cart.api.template.dto.getByIdDto

import jakarta.validation.constraints.NotBlank

data class GetTemplateByIdRequestParamsDto (
    @field:NotBlank
    val id: Long
)