package kr.co.shoppingcart.cart.domain.user.services

import kr.co.shoppingcart.cart.domain.user.UserRepository
import kr.co.shoppingcart.cart.domain.user.command.LoginCommand
import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.springframework.stereotype.Component

@Component
class UserCreationService(
    private val userRepository: UserRepository,
) {
    fun createUser(loginCommand: LoginCommand): User =
        userRepository.create(
            loginCommand.email,
            LoginProvider.valueOf(loginCommand.loginProvider),
            loginCommand.authIdentifier,
            loginCommand.gender,
            loginCommand.ageRange,
        )
}
