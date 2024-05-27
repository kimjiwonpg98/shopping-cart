package kr.co.shoppingcart.cart.domain.user.command

import jakarta.validation.constraints.Email
import kr.co.shoppingcart.cart.common.SelfValidating
import kr.co.shoppingcart.cart.common.validator.ValueOfEnum
import kr.co.shoppingcart.cart.domain.user.enums.LoginType

data class LoginCommand (
    @field:Email
    val email: String,
    @field:ValueOfEnum(enumClass = LoginType::class, message = "E_400_000")
    val loginType: String,
): SelfValidating<LoginCommand>() {
    init {
        this.validateSelf()
    }
}