package kr.co.shoppingcart.cart.api.basket.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class CheckedAllBasketReqBodyDto(
    @field:Min(1)
    @field:NotNull
    val templateId: Long,
)
