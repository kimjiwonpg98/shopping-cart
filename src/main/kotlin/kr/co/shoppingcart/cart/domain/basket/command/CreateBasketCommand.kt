package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class CreateBasketCommand(
    @NotNull val templatedId: Long,
    @NotNull val name: String,
    @NotNull val categoryId: Long,
    @NotNull val userId: Long,
    val isAdded: Boolean? = false,
    val count: Long? = 1,
): SelfValidating<CreateBasketCommand>() {
    init {
        this.validateSelf()
    }
}
