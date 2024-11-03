package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class UpdateBasketContentCommand(
    @field:NotNull
    val userId: Long,
    @field:NotNull
    val basketId: Long,
    val content: String? = null,
    val count: Long? = null,
) : SelfValidating<UpdateBasketContentCommand>() {
    init {
        this.validateSelf()
    }
}
