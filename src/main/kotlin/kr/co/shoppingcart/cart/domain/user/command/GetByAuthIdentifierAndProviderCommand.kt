package kr.co.shoppingcart.cart.domain.user.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating
import kr.co.shoppingcart.cart.common.validator.ValueOfEnum
import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider

data class GetByAuthIdentifierAndProviderCommand(
    @field:NotNull
    val authIdentifier: String,
    @field:ValueOfEnum(enumClass = LoginProvider::class, message = "E_400_000")
    val loginProvider: String,
) : SelfValidating<GetByAuthIdentifierAndProviderCommand>() {
    init {
        this.validateSelf()
    }
}
