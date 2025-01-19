package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class UpdateAllCheckedCommand(
    @field:NotNull
    @field:Min(1)
    val templateId: Long,
    @field:NotNull
    @field:Min(1)
    val userId: Long,
) : SelfValidating<UpdateAllCheckedCommand>() {
    init {
        this.validateSelf()
    }
}
