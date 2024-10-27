package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull

data class DeleteBasketByIdCommand(
    @field:NotNull
    val basketId: Long,
    @field:NotNull
    val userId: Long,
)
