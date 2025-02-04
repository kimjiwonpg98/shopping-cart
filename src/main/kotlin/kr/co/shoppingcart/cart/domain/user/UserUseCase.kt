package kr.co.shoppingcart.cart.domain.user

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.permissions.services.PermissionService
import kr.co.shoppingcart.cart.domain.permissions.vo.SeparatePermissions
import kr.co.shoppingcart.cart.domain.template.services.DeleteTemplateService
import kr.co.shoppingcart.cart.domain.user.command.DeleteUserCommand
import kr.co.shoppingcart.cart.domain.user.command.GetByAuthIdentifierAndProviderCommand
import kr.co.shoppingcart.cart.domain.user.command.LoginCommand
import kr.co.shoppingcart.cart.domain.user.services.GetUserService
import kr.co.shoppingcart.cart.domain.user.services.UserCreationService
import kr.co.shoppingcart.cart.domain.user.services.UserUpdateService
import kr.co.shoppingcart.cart.domain.user.vo.User
import kr.co.shoppingcart.cart.external.kakao.KakaoClient
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class UserUseCase(
    private val userCreationService: UserCreationService,
    private val getUserService: GetUserService,
    private val userUpdateService: UserUpdateService,
    @Qualifier("ownerPermissionService")
    private val ownerPermissionService: PermissionService,
    private val deleteTemplateService: DeleteTemplateService,
    private val kakaoClient: KakaoClient,
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
    fun deleteUser(command: DeleteUserCommand): User {
        val user = userUpdateService.deleteUser(command.userId, command.loginProvider)
        val permissions = ownerPermissionService.getByUserId(user.userId.id)
        val separatedPermissions = SeparatePermissions.toDomain(permissions)

        deleteTemplateService.deleteByIds(separatedPermissions.ownerPermissions.map { it.templateId.templateId })
        ownerPermissionService.deleteByIds(
            separatedPermissions.otherPermissions.map {
                it.id.id
            },
        )
        userUpdateService.unlinkProvider(user)
        return user
    }

    @Transactional(readOnly = true)
    fun getById(id: Long): User =
        this.getUserService.getById(id)
            ?: throw CustomException.responseBody(ExceptionCode.E_401_000)
}
