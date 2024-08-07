package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class GetBasketsByTemplateIdCommand(
    @NotNull val templateId: Long,
    @NotNull val userId: Long,
    @NotNull val page: Long,
    @NotNull val size: Long,
) : SelfValidating<GetBasketsByTemplateIdCommand>() {
    init {
        this.validateSelf()
    }
}
