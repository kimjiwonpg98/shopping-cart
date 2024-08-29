package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class DeleteByTemplateIdCommand(
    @field:NotNull
    val templateId: Long,
    @field:NotNull
    val userId: Long,
) : SelfValidating<DeleteByTemplateIdCommand>() {
    init {
        validateSelf()
    }
}
