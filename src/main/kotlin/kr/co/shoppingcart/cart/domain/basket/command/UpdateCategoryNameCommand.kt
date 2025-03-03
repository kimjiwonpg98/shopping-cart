package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class UpdateCategoryNameCommand(
    val basketIds: List<Long>,
    @field:NotNull
    val categoryName: String,
    @field:NotNull
    val userId: Long,
) : SelfValidating<UpdateCategoryNameCommand>() {
    init {
        this.validateSelf()
    }
}
