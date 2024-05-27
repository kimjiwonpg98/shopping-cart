package kr.co.shoppingcart.cart.domain.user.vo


data class User(
    val userEmail: UserEmail,
    val loginType: UserLoginType,
    val userId: UserId
)
