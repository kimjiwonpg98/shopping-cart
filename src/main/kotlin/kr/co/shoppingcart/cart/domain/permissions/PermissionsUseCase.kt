package kr.co.shoppingcart.cart.domain.permissions

import kr.co.shoppingcart.cart.domain.permissions.command.CreateOwnerPermissionCommand
import kr.co.shoppingcart.cart.domain.permissions.command.CreateWriterPermissionCommand
import kr.co.shoppingcart.cart.domain.permissions.command.GetPermissionByUserIdAndTemplateIdCommand
import kr.co.shoppingcart.cart.domain.permissions.vo.Permissions
import org.springframework.stereotype.Service

@Service
class PermissionsUseCase(
    private val permissionsRepository: PermissionsRepository,
) {
    fun getByTemplateIdAndUserId(command: GetPermissionByUserIdAndTemplateIdCommand): Permissions? =
        permissionsRepository.getByUserIdAndTemplateId(command.userId, command.templateId)

    fun createOwnerPermission(command: CreateOwnerPermissionCommand): Permissions {
        val permission = Permissions.toDomain(command.userId, command.templateId, 0)
        return permissionsRepository.create(permission)
    }

    fun createWriterPermission(command: CreateWriterPermissionCommand): Permissions {
        val permission = Permissions.toDomain(command.userId, command.templateId, 1)
        return permissionsRepository.create(permission)
    }

    fun deletePermission(
        templateId: Long,
        userId: Long,
    ) = permissionsRepository.deleteByUserIdAndTemplateId(userId, templateId)
}
