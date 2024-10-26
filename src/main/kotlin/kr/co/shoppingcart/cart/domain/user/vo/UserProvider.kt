package kr.co.shoppingcart.cart.domain.user.vo

import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider

data class UserProvider(
    val provider: LoginProvider,
)
