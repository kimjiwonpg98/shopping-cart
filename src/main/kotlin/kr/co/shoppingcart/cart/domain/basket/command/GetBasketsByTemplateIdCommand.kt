package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class GetBasketsByTemplateIdCommand(
    @field:Min(1)
    @field:NotNull
    val templateId: Long,
    @field:Min(1)
    @field:NotNull
    val userId: Long,
) : SelfValidating<GetBasketsByTemplateIdCommand>() {
    init {
        this.validateSelf()
    }
}
