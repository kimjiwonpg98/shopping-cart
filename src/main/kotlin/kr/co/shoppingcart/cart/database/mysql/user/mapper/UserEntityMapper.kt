package kr.co.shoppingcart.cart.database.mysql.user.mapper

import kr.co.shoppingcart.cart.database.mysql.user.entity.UserEntity
import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider
import kr.co.shoppingcart.cart.domain.user.vo.User
import kr.co.shoppingcart.cart.domain.user.vo.UserAgeRange
import kr.co.shoppingcart.cart.domain.user.vo.UserAuthIdentifier
import kr.co.shoppingcart.cart.domain.user.vo.UserEmail
import kr.co.shoppingcart.cart.domain.user.vo.UserGender
import kr.co.shoppingcart.cart.domain.user.vo.UserId
import kr.co.shoppingcart.cart.domain.user.vo.UserProvider

object UserEntityMapper {
    fun toDomain(userEntity: UserEntity): User =
        User(
            userEmail = UserEmail(userEntity.email),
            provider = UserProvider(LoginProvider.valueOf(userEntity.provider)),
            userId = UserId(userEntity.id!!),
            authIdentifier = UserAuthIdentifier(userEntity.authIdentifier),
            gender = UserGender(userEntity.gender),
            ageRange = UserAgeRange(userEntity.ageRange),
        )
}
