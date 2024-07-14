package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class UpdateTemplateSharedByIdCommand(
    @field:NotNull
    val id: Long,
    @field:NotNull
    val userId: Long,
    @field:NotNull
    val isShared: Boolean,
) : SelfValidating<UpdateTemplateSharedByIdCommand>() {
    init {
        this.validateSelf()
    }
}
