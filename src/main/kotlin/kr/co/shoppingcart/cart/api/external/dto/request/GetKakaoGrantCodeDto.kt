package kr.co.shoppingcart.cart.api.external.dto.request

import jakarta.validation.constraints.NotNull

data class GetKakaoGrantCodeDto(
    @field:NotNull
    val code: String,
)
