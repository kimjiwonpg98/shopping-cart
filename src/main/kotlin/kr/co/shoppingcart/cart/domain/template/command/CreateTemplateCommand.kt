package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import kr.co.shoppingcart.cart.common.SelfValidating

data class CreateTemplateCommand(
    @field:NotNull
    @field:Size(min = 1, max = 30)
    val name: String,
    @field:NotNull
    @field:Min(1)
    val userId: Long,
) : SelfValidating<CreateTemplateCommand>() {
    init {
        this.validateSelf()
    }
}
