package kr.co.shoppingcart.cart.core.permission.application

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.core.permission.application.port.input.GetPermissionByUser
import kr.co.shoppingcart.cart.core.permission.application.port.input.GetPermissionByUserIdAndTemplateIdCommand
import kr.co.shoppingcart.cart.core.permission.application.port.input.SeparateOwnerAndOtherRes
import kr.co.shoppingcart.cart.core.permission.application.port.input.SeparatePermission
import kr.co.shoppingcart.cart.core.permission.application.port.input.ValidPermission
import kr.co.shoppingcart.cart.core.permission.application.port.input.ValidPermissionCommand
import kr.co.shoppingcart.cart.core.permission.application.port.input.ValidPermissionIsOverLevelCommand
import kr.co.shoppingcart.cart.core.permission.application.port.output.PermissionsRepository
import kr.co.shoppingcart.cart.core.permission.domain.Permission
import kr.co.shoppingcart.cart.core.permission.domain.PermissionLevel
import org.springframework.stereotype.Service

@Service
class GetPermissionsService(
    private val permissionsRepository: PermissionsRepository<Permission, Long>,
) : GetPermissionByUser,
    ValidPermission,
    SeparatePermission {
    override fun getByUserIdAndTemplateId(command: GetPermissionByUserIdAndTemplateIdCommand): Permission? =
        permissionsRepository.findOneByUserIdAndTemplateId(
            command.userId,
            command.templateId,
        )

    override fun getByUserId(userId: Long): List<Permission> = permissionsRepository.findByUserId(userId)

    override fun validate(command: ValidPermissionCommand) {
        val permission =
            permissionsRepository.findOneByUserIdAndTemplateId(
                command.userId,
                command.templateId,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        if (command.level.level != permission.level.level) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }
    }

    override fun isOverLevel(command: ValidPermissionIsOverLevelCommand) {
        val permission =
            permissionsRepository.findOneByUserIdAndTemplateId(
                command.userId,
                command.templateId,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        if (command.level.level < permission.level.level) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }
    }

    override fun separateOwnerAndOther(permissions: List<Permission>): SeparateOwnerAndOtherRes =
        permissions.partition { it.level.level == PermissionLevel.OWNER_LEVEL.level }.let {
            SeparateOwnerAndOtherRes(
                ownerPermissions = it.first,
                otherPermissions = it.second,
            )
        }
}
