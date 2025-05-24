package kr.co.shoppingcart.cart.api.basket.adapter.input.web.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class CheckAllBasketRequest(
    @field:Min(1)
    @field:NotNull
    val templateId: Long,
)
