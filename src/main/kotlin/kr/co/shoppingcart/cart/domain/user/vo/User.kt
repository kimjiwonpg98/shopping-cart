package kr.co.shoppingcart.cart.domain.user.vo

import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider

data class User(
    val userEmail: UserEmail,
    val provider: UserProvider,
    val userId: UserId,
    val authIdentifier: UserAuthIdentifier,
    val gender: UserGender?,
    val ageRange: UserAgeRange?,
) {
    companion object {
        fun toDomain(
            email: String?,
            provider: String,
            userId: Long = 0,
            authIdentifier: String,
            gender: String?,
            ageRange: String?,
        ): User =
            User(
                userEmail = UserEmail(email),
                provider = UserProvider(LoginProvider.valueOf(provider)),
                userId = UserId(userId),
                authIdentifier = UserAuthIdentifier(authIdentifier),
                gender = gender?.let { UserGender(it) },
                ageRange = ageRange?.let { UserAgeRange(it) },
            )
    }
}
