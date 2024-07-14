package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class UpdateBasketFlagCommand(
    @NotNull val basketId: Long,
    @NotNull val checked: Boolean,
    @NotNull val userId: Long,
) : SelfValidating<CreateBasketCommand>() {
    init {
        this.validateSelf()
    }
}
