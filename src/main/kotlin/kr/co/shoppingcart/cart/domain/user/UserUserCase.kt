package kr.co.shoppingcart.cart.domain.user

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.user.command.GetByAuthIdentifierAndProviderCommand
import kr.co.shoppingcart.cart.domain.user.command.LoginCommand
import kr.co.shoppingcart.cart.domain.user.enums.LoginProvider
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class UserUserCase(
    private val userRepository: UserRepository,
) {
    @Transactional
    fun createIfAbsent(loginCommand: LoginCommand): User =
        this.getByAuthIdentifierAndProvider(
            GetByAuthIdentifierAndProviderCommand(
                loginCommand.authIdentifier,
                loginCommand.loginProvider,
            ),
        ) ?: this.createUser(loginCommand)

    @Transactional(propagation = Propagation.MANDATORY)
    fun createUser(loginCommand: LoginCommand): User =
        userRepository.create(
            loginCommand.email,
            LoginProvider.valueOf(loginCommand.loginProvider),
            loginCommand.authIdentifier,
            loginCommand.gender,
            loginCommand.ageRange,
        )

    @Transactional(readOnly = true)
    fun getByAuthIdentifierAndProviderOrFail(command: GetByAuthIdentifierAndProviderCommand): User =
        this.getByAuthIdentifierAndProvider(command) ?: throw CustomException.responseBody(ExceptionCode.E_401_000)

    fun getByAuthIdentifierAndProvider(command: GetByAuthIdentifierAndProviderCommand): User? =
        userRepository.getByUniqueKeyAndLoginType(
            command.authIdentifier,
            LoginProvider.valueOf(command.loginProvider),
        )
}
