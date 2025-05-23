package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class DeleteBasketByIdCommand(
    @field:NotNull
    val basketId: Long,
    @field:NotNull
    val userId: Long,
) : SelfValidating<DeleteBasketByIdCommand>() {
    init {
        this.validateSelf()
    }
}
