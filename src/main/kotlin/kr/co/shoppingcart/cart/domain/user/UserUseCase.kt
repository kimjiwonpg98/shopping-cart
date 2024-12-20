package kr.co.shoppingcart.cart.domain.user

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.permissions.services.OwnerPermissionService
import kr.co.shoppingcart.cart.domain.template.services.DeleteTemplateService
import kr.co.shoppingcart.cart.domain.user.command.DeleteUserCommand
import kr.co.shoppingcart.cart.domain.user.command.GetByAuthIdentifierAndProviderCommand
import kr.co.shoppingcart.cart.domain.user.command.LoginCommand
import kr.co.shoppingcart.cart.domain.user.services.GetUserService
import kr.co.shoppingcart.cart.domain.user.services.UserCreationService
import kr.co.shoppingcart.cart.domain.user.services.UserUpdateService
import kr.co.shoppingcart.cart.domain.user.vo.User
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class UserUseCase(
    private val userCreationService: UserCreationService,
    private val getUserService: GetUserService,
    private val userUpdateService: UserUpdateService,
    private val ownerPermissionService: OwnerPermissionService,
    private val deleteTemplateService: DeleteTemplateService,
) {
    @Transactional
    fun createIfAbsent(loginCommand: LoginCommand): User =
        this.getUserService.getByAuthIdentifierAndProvider(
            GetByAuthIdentifierAndProviderCommand(
                loginCommand.authIdentifier,
                loginCommand.loginProvider,
            ),
        ) ?: this.createUser(loginCommand)

    @Transactional(propagation = Propagation.MANDATORY)
    fun createUser(loginCommand: LoginCommand): User = userCreationService.createUser(loginCommand)

    @Transactional(readOnly = true)
    fun getByAuthIdentifierAndProviderOrFail(command: GetByAuthIdentifierAndProviderCommand): User =
        this.getUserService.getByAuthIdentifierAndProvider(command)
            ?: throw CustomException.responseBody(ExceptionCode.E_401_000)

    @Transactional
    fun deleteUser(command: DeleteUserCommand) {
        val user = userUpdateService.deleteUser(command.authIdentifier, command.loginProvider)
        val permissions = ownerPermissionService.getByUserId(user.userId.id)

        deleteTemplateService.deleteByIds(permissions.ownerPermissions.map { it.templateId.templateId })
        ownerPermissionService.deleteByIds(
            permissions.otherPermissions.map {
                it.id.id
            },
        )
    }
}
