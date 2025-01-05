package kr.co.shoppingcart.cart.domain.permissions.services

import kr.co.shoppingcart.cart.domain.permissions.PermissionsRepository
import kr.co.shoppingcart.cart.domain.permissions.enums.PermissionLevelEnum
import kr.co.shoppingcart.cart.domain.permissions.vo.Permission
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class OwnerPermissionService(
    private val permissionsRepository: PermissionsRepository,
) : PermissionService {
    @Transactional(propagation = Propagation.MANDATORY)
    override fun createPermission(
        userId: Long,
        templateId: Long,
    ): Permission =
        permissionsRepository.createPermissionByLevel(
            userId,
            templateId,
            PermissionLevelEnum.OWNER_LEVEL.level,
        )

    override fun getOverLevelByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permission? {
        val permission = permissionsRepository.getByUserIdAndTemplateId(userId, templateId) ?: return null

        if (!permission.level.isOverOwnerLevel()) {
            return null
        }

        return permission
    }

    override fun getByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permission? {
        val permission = permissionsRepository.getByUserIdAndTemplateId(userId, templateId) ?: return null

        if (!permission.level.isOwnerLevel()) {
            return null
        }

        return permission
    }

    override fun getByUserId(userId: Long): List<Permission> = permissionsRepository.getByUserId(userId)

    @Transactional(propagation = Propagation.MANDATORY)
    fun deleteByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ) = permissionsRepository.deleteByUserIdAndTemplateId(userId, templateId)

    @Transactional(propagation = Propagation.MANDATORY)
    override fun deleteByIds(ids: List<Long>) = permissionsRepository.deleteByIds(ids)
}
