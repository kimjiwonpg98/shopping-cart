package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class CopyTemplateCommand(
    @field:NotNull
    @field:Min(1)
    val id: Long,
    @field:NotNull
    @field:Min(1)
    val userId: Long,
) : SelfValidating<CopyTemplateCommand>() {
    init {
        this.validateSelf()
    }
}
