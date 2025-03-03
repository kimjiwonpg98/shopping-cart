package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class GetPublicBasketByTemplateIdAndCategoryNameCommand(
    @field:NotNull
    val templateId: Long,
    @field:NotNull
    val categoryName: String,
) : SelfValidating<GetPublicBasketByTemplateIdAndCategoryNameCommand>() {
    init {
        this.validateSelf()
    }
}
