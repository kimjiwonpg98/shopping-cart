package kr.co.shoppingcart.cart.domain.cart.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating


data class CreateCartCommand(
    @NotNull val cartName: String,
    @NotNull val userId: Long,
): SelfValidating<CreateCartCommand>() {
    init {
        this.validateSelf()
    }
}