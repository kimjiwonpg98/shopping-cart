package kr.co.shoppingcart.cart.domain.permissions.services

import kr.co.shoppingcart.cart.domain.permissions.PermissionsRepository
import kr.co.shoppingcart.cart.domain.permissions.enums.PermissionLevelEnum
import kr.co.shoppingcart.cart.domain.permissions.vo.Permissions
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class OwnerPermissionService(
    private val permissionsRepository: PermissionsRepository,
) {
    @Transactional(propagation = Propagation.MANDATORY)
    fun createPermission(
        userId: Long,
        templateId: Long,
    ): Permissions =
        permissionsRepository.createPermissionByLevel(
            userId,
            templateId,
            PermissionLevelEnum.OWNER_LEVEL.level,
        )

    fun getByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permissions? {
        val permission = permissionsRepository.getByUserIdAndTemplateId(userId, templateId) ?: return null

        if (!permission.level.isOwnerLevel()) {
            return null
        }

        return permission
    }

    @Transactional(propagation = Propagation.MANDATORY)
    fun deleteByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ) = permissionsRepository.deleteByUserIdAndTemplateId(userId, templateId)
}
