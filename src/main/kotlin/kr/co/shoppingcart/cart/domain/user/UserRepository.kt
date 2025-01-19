package kr.co.shoppingcart.cart.domain.user

import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider
import kr.co.shoppingcart.cart.domain.user.vo.User

interface UserRepository {
    fun create(
        email: String?,
        provider: LoginProvider = LoginProvider.KAKAO,
        authIdentifier: String,
        gender: String?,
        ageRange: String?,
    ): User

    fun getByUniqueKeyAndLoginType(
        authIdentifier: String,
        provider: LoginProvider,
    ): User?

    fun findById(id: Long): User?

    fun deleteByUniqueKeyAndLoginType(
        authIdentifier: String,
        provider: LoginProvider,
    ): User?

    fun deleteByIdAndLoginType(
        id: Long,
        provider: LoginProvider,
    ): User?
}
