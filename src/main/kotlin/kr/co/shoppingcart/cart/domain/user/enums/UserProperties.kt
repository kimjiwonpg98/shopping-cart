package kr.co.shoppingcart.cart.domain.user.enums

import kr.co.shoppingcart.cart.domain.user.vo.User
import java.util.Arrays

enum class UserProperties(
    val registrationId: String,
    val of: (Map<String, Any>) -> User,
) {
    KAKAO("kakao", { attribute ->
        val id = attribute["id"] as Long
        val kakaoAccount = attribute["kakao_account"] as Map<*, *>? ?: emptyMap<String, String>()
        val email = kakaoAccount["email"] as String?
        val ageRange = kakaoAccount["age_range"] as String?
        val gender = kakaoAccount["gender"] as String?

        User.toDomain(
            email = email,
            provider = LoginProvider.KAKAO.name,
            authIdentifier = id.toString(),
            gender = gender,
            ageRange = ageRange,
        )
    }),
    ;

    fun extract(
        registrationId: String,
        attributes: Map<String, Any>,
    ): User =
        Arrays
            .stream(entries.toTypedArray())
            .filter { provider -> registrationId == provider.registrationId }
            .findFirst()
            .orElseThrow { IllegalArgumentException() }
            .of(attributes)
}
