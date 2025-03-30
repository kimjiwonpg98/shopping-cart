package kr.co.shoppingcart.cart.core.permission.application

import kr.co.shoppingcart.cart.core.permission.application.port.input.CreatePermission
import kr.co.shoppingcart.cart.core.permission.application.port.input.CreatePermissionCommand
import kr.co.shoppingcart.cart.core.permission.application.port.input.DeletePermission
import kr.co.shoppingcart.cart.core.permission.application.port.input.DeletePermissionByIdsCommand
import kr.co.shoppingcart.cart.core.permission.application.port.input.DeletePermissionCommand
import kr.co.shoppingcart.cart.core.permission.application.port.output.PermissionsRepository
import kr.co.shoppingcart.cart.core.permission.domain.Permission
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class PermissionService(
    private val permissionsRepository: PermissionsRepository<Permission, Long>,
) : CreatePermission,
    DeletePermission {
    override fun create(command: CreatePermissionCommand) {
        permissionsRepository.save(command.of())
    }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun delete(command: DeletePermissionCommand) {
        permissionsRepository.deleteByUserIdAndTemplateId(command.userId, command.templateId)
    }

    @Transactional(propagation = Propagation.MANDATORY)
    override fun deleteByIds(command: DeletePermissionByIdsCommand) {
        permissionsRepository.deleteByIdIn(command.ids)
    }
}
