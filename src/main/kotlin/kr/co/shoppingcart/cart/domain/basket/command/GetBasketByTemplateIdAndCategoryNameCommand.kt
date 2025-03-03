package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class GetBasketByTemplateIdAndCategoryNameCommand(
    @field:NotNull
    val templateId: Long,
    @field:NotNull
    val categoryName: String,
    @field:NotNull
    val userId: Long,
) : SelfValidating<GetBasketByTemplateIdAndCategoryNameCommand>() {
    init {
        this.validateSelf()
    }
}
