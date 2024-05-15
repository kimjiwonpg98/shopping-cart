package kr.co.shoppingcart.cart.domain.user.vo


data class User(
    private val userEmail: UserEmail,
    private val loginType: UserLoginType
)
