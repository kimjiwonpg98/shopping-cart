package kr.co.shoppingcart.cart.domain.permissions.services

import kr.co.shoppingcart.cart.domain.permissions.PermissionsRepository
import kr.co.shoppingcart.cart.domain.permissions.enums.PermissionLevelEnum
import kr.co.shoppingcart.cart.domain.permissions.vo.Permissions
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class ReaderPermissionService(
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
            PermissionLevelEnum.WRITER_LEVEL.level,
        )

    fun getOverLevelByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permissions? {
        val permission = permissionsRepository.getByUserIdAndTemplateId(userId, templateId) ?: return null

        if (!permission.level.isOverReaderLevel()) {
            return null
        }

        return permission
    }
}
