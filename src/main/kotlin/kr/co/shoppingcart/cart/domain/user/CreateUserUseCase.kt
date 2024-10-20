package kr.co.shoppingcart.cart.domain.user

import kr.co.shoppingcart.cart.domain.user.command.LoginCommand
import kr.co.shoppingcart.cart.domain.user.enums.LoginType
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateUserUseCase(
    private val userRepository: UserRepository,
) {
    @Transactional(readOnly = false)
    fun createUser(loginCommand: LoginCommand): User =
        userRepository.create(
            loginCommand.email,
            LoginType.valueOf(loginCommand.loginType),
            loginCommand.gender,
            loginCommand.birth,
        )
}
