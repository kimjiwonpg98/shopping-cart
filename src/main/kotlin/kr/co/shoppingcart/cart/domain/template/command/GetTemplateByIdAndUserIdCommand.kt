package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class GetTemplateByIdAndUserIdCommand(
    @field:NotNull
    val id: String,
    @field:NotNull
    val userId: Long
): SelfValidating<GetTemplateByIdAndUserIdCommand>() {
    init {
        this.validateSelf()
    }
}
