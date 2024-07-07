package kr.co.shoppingcart.cart.domain.user

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.user.command.LoginCommand
import kr.co.shoppingcart.cart.domain.user.enums.LoginType
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.springframework.stereotype.Service

@Service
class GetUserUseCase (
    private val userRepository: UserRepository
) {
    fun getByEmailAndLoginType(loginCommand: LoginCommand): User =
        userRepository.getByEmailAndLoginType(loginCommand.email, LoginType.valueOf(loginCommand.loginType))
            ?: throw CustomException.responseBody(ExceptionCode.E_401_000)
}