package kr.co.shoppingcart.cart.domain.user.command

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating
import kr.co.shoppingcart.cart.common.validator.ValueOfEnum
import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider

data class LoginCommand(
    @field:Email(message = "E_400_000")
    val email: String? = null,
    @field:ValueOfEnum(enumClass = LoginProvider::class, message = "E_400_000")
    val loginProvider: String,
    @field:NotNull
    val authIdentifier: String,
    val gender: String?,
    val ageRange: String?,
) : SelfValidating<LoginCommand>() {
    init {
        this.validateSelf()
    }
}
