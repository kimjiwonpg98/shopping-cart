package kr.co.shoppingcart.cart.database.mysql.user.mapper

import kr.co.shoppingcart.cart.database.mysql.user.entity.UserEntity
import kr.co.shoppingcart.cart.domain.user.enums.LoginType
import kr.co.shoppingcart.cart.domain.user.vo.User
import kr.co.shoppingcart.cart.domain.user.vo.UserBirth
import kr.co.shoppingcart.cart.domain.user.vo.UserEmail
import kr.co.shoppingcart.cart.domain.user.vo.UserGender
import kr.co.shoppingcart.cart.domain.user.vo.UserId
import kr.co.shoppingcart.cart.domain.user.vo.UserLoginType

object UserEntityMapper {
    fun toDomain(userEntity: UserEntity): User =
        User(
            userEmail = UserEmail(userEntity.email),
            loginType = UserLoginType(LoginType.valueOf(userEntity.loginType)),
            userId = UserId(userEntity.id!!),
            gender = UserGender(userEntity.gender),
            birth = UserBirth(userEntity.birth),
        )
}
