package kr.co.shoppingcart.cart.api.basket.dto.request

import jakarta.validation.constraints.NotNull

data class DeleteBasketsReqBodyDto(
    @field:NotNull
    val basketIds: List<Long>,
)
