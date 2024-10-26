package kr.co.shoppingcart.cart.api.basket.dto.request

import jakarta.validation.constraints.NotNull

data class UpdateBasketContentReqBodyDto(
    val content: String?,
    val count: Long?,
    @field:NotNull
    val basketId: Long,
)
