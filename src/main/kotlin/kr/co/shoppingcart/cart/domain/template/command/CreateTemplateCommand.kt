package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class CreateTemplateCommand(
    @NotNull val name: String,
    @NotNull val userId: Long,
) : SelfValidating<CreateTemplateCommand>() {
    init {
        this.validateSelf()
    }
}
