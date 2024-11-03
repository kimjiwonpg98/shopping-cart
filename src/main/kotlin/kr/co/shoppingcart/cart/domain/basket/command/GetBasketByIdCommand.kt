package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class GetBasketByIdCommand(
    @field:NotNull
    val userId: Long,
    @field:NotNull
    val basketId: Long,
) : SelfValidating<GetBasketByIdCommand>() {
    init {
        this.validateSelf()
    }
}
