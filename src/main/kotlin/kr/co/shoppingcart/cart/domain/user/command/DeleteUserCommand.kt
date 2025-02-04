package kr.co.shoppingcart.cart.domain.user.command

import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider

data class DeleteUserCommand(
    val userId: Long,
    val loginProvider: LoginProvider,
)
