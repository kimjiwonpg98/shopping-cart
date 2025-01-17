package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class GetPublicBasketByTemplateIdAndCategoryIdCommand(
    @field:NotNull
    val templateId: Long,
    @field:NotNull
    val categoryId: Long,
) : SelfValidating<GetPublicBasketByTemplateIdAndCategoryIdCommand>() {
    init {
        this.validateSelf()
    }
}
