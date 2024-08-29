package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class UpdateBasketFlagCommand(
    @field:NotNull val basketId: Long,
    @field:NotNull val checked: Boolean,
    @field:NotNull val userId: Long,
) : SelfValidating<CreateBasketCommand>() {
    init {
        this.validateSelf()
    }
}
