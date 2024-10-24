package kr.co.shoppingcart.cart.domain.user.enums

import kr.co.shoppingcart.cart.domain.user.vo.User
import java.util.Arrays

enum class UserProperties(
    val registrationId: String,
    val of: (Map<String, Any>) -> User,
) {
    KAKAO("kakao", { attribute ->
        val pro = attribute["profile_nickname_needs_agreement"] as Boolean? ?: false

        User.toDomain(
            email = pro.toString(),
            loginType = LoginType.KAKAO.name,
            gender = pro.toString(),
            birth = pro.toString(),
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
