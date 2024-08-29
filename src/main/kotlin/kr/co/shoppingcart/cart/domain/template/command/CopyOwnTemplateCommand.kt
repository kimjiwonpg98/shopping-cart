package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class CopyOwnTemplateCommand(
    @field:Min(1)
    @field:NotNull
    val id: Long,
    @field:Min(1)
    @field:NotNull
    val userId: Long,
) : SelfValidating<CopyOwnTemplateCommand>() {
    init {
        this.validateSelf()
    }
}
