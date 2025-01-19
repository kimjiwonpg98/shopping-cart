package kr.co.shoppingcart.cart.domain.user.services

import kr.co.shoppingcart.cart.domain.user.UserRepository
import kr.co.shoppingcart.cart.domain.user.command.GetByAuthIdentifierAndProviderCommand
import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.springframework.stereotype.Component

@Component
class GetUserService(
    private val userRepository: UserRepository,
) {
    fun getByAuthIdentifierAndProvider(command: GetByAuthIdentifierAndProviderCommand): User? =
        userRepository.getByUniqueKeyAndLoginType(
            command.authIdentifier,
            LoginProvider.valueOf(command.loginProvider),
        )

    fun getById(id: Long): User? = userRepository.findById(id)
}
