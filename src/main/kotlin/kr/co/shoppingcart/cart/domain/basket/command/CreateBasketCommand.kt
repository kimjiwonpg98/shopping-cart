package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class CreateBasketCommand(
    @field:Min(1)
    @field:NotNull
    val templateId: Long,
    @field:NotBlank
    @field:NotNull
    val name: String,
    @field:NotNull
    val categoryName: String,
    @field:NotNull
    val userId: Long,
    val checked: Boolean? = false,
    val count: Long? = 1,
) : SelfValidating<CreateBasketCommand>() {
    init {
        this.validateSelf()
    }
}
