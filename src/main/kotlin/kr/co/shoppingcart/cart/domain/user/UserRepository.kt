package kr.co.shoppingcart.cart.domain.user

import kr.co.shoppingcart.cart.domain.user.enums.LoginType
import kr.co.shoppingcart.cart.domain.user.vo.User

interface UserRepository {
    fun create(email: String, loginType: LoginType = LoginType.KAKAO): User
}