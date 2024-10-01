package kr.co.shoppingcart.cart.domain.user.vo

import kr.co.shoppingcart.cart.domain.user.enums.LoginType

data class User(
    val userEmail: UserEmail,
    val loginType: UserLoginType,
    val userId: UserId,
    val gender: UserGender?,
    val birth: UserBirth?,
) {
    companion object {
        fun toDomain(
            email: String,
            loginType: String,
            userId: Long,
            gender: String?,
            birth: String?,
        ): User =
            User(
                userEmail = UserEmail(email),
                loginType = UserLoginType(LoginType.valueOf(loginType)),
                userId = UserId(userId),
                gender = gender?.let { UserGender(it) },
                birth = birth?.let { UserBirth(it) },
            )
    }
}
