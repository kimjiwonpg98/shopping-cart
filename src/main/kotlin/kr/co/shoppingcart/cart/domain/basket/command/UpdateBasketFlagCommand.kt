package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class UpdateBasketFlagCommand(
    @field:Min(1)
    @field:NotNull
    val basketId: Long,
    @field:NotNull
    val checked: Boolean,
    @field:Min(1)
    @field:NotNull
    val userId: Long,
) : SelfValidating<CreateBasketCommand>() {
    init {
        this.validateSelf()
    }
}
