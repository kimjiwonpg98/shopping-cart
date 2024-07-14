package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class CopyTemplateInCompleteCommand(
    @field:NotNull
    val id: Long,
    @field:NotNull
    val userId: Long,
) : SelfValidating<CopyTemplateInCompleteCommand>() {
    init {
        this.validateSelf()
    }
}
