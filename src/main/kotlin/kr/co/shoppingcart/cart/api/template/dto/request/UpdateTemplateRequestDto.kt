package kr.co.shoppingcart.cart.api.template.dto.request

import jakarta.annotation.Nullable

data class UpdateTemplateRequestDto(
    @field:Nullable
    val name: String? = null,
    @field:Nullable
    val thumbnailIndex: Int? = null,
)
