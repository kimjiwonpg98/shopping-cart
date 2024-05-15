package kr.co.shoppingcart.cart.domain.user.mapper

import kr.co.shoppingcart.cart.database.mysql.user.entity.UserEntity
import kr.co.shoppingcart.cart.domain.user.enums.LoginType
import kr.co.shoppingcart.cart.domain.user.vo.UserEmail
import kr.co.shoppingcart.cart.domain.user.vo.User
import kr.co.shoppingcart.cart.domain.user.vo.UserLoginType

object UserJpaMapper {
    fun toDomain(userEntity: UserEntity): User = User(
        userEmail =  UserEmail(userEntity.email),
        loginType = UserLoginType(LoginType.valueOf(userEntity.loginType)),
    )
}