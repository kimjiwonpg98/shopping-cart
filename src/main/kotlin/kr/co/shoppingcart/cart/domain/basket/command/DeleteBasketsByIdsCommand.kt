package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class DeleteBasketsByIdsCommand(
    @field:NotNull
    val basketIds: List<Long>,
    @field:NotNull
    val userId: Long,
) : SelfValidating<DeleteBasketsByIdsCommand>() {
    init {
        this.validateSelf()
    }
}
