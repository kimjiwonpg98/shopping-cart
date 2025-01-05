package kr.co.shoppingcart.cart.mock

import kr.co.shoppingcart.cart.domain.permissions.PermissionsRepository
import kr.co.shoppingcart.cart.domain.permissions.vo.Permission
import kr.co.shoppingcart.cart.domain.permissions.vo.PermissionId
import kr.co.shoppingcart.cart.domain.permissions.vo.PermissionLevel
import kr.co.shoppingcart.cart.domain.permissions.vo.PermissionTemplateId
import kr.co.shoppingcart.cart.domain.permissions.vo.PermissionUserId
import kr.co.shoppingcart.cart.mock.vo.MockPermissions

class MockPermissionsRepository : PermissionsRepository {
    override fun getByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permission? = MockPermissions.getOptionalPermission(userId, PERMISSION_ID_RETURN_NULL != userId)

    override fun getByUserId(userId: Long): List<Permission> {
        TODO("Not yet implemented")
    }

    override fun create(permission: Permission): Permission = permission

    override fun createPermissionByLevel(
        userId: Long,
        templateId: Long,
        level: Int,
    ): Permission =
        Permission(
            id = PermissionId(1),
            userId = PermissionUserId(userId),
            templateId = PermissionTemplateId(templateId),
            level = PermissionLevel(level),
        )

    override fun deleteByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ) {
    }

    override fun deleteByIds(ids: List<Long>) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val PERMISSION_ID_RETURN_NULL = 100L
    }
}
