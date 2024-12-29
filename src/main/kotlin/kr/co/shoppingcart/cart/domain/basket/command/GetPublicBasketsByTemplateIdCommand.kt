package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.Min
import kr.co.shoppingcart.cart.common.SelfValidating

data class GetPublicBasketsByTemplateIdCommand(
    @field:Min(1)
    val templateId: Long,
) : SelfValidating<GetPublicBasketsByTemplateIdCommand>() {
    init {
        this.validateSelf()
    }
}
