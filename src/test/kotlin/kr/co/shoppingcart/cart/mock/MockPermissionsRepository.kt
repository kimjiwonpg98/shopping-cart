package kr.co.shoppingcart.cart.mock

import kr.co.shoppingcart.cart.domain.permissions.PermissionsRepository
import kr.co.shoppingcart.cart.domain.permissions.vo.PermissionLevel
import kr.co.shoppingcart.cart.domain.permissions.vo.PermissionTemplateId
import kr.co.shoppingcart.cart.domain.permissions.vo.PermissionUserId
import kr.co.shoppingcart.cart.domain.permissions.vo.Permissions
import kr.co.shoppingcart.cart.mock.vo.MockPermissions

class MockPermissionsRepository : PermissionsRepository {
    override fun getByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permissions? = MockPermissions.getOptionalPermission(userId, PERMISSION_ID_RETURN_NULL != userId)

    override fun create(permission: Permissions): Permissions = permission

    override fun createOwnerPermission(
        userId: Long,
        templateId: Long,
    ): Permissions =
        Permissions(
            userId = PermissionUserId(userId),
            templateId = PermissionTemplateId(templateId),
            level = PermissionLevel(0),
        )

    override fun createWriterPermission(
        userId: Long,
        templateId: Long,
    ): Permissions =
        Permissions(
            userId = PermissionUserId(userId),
            templateId = PermissionTemplateId(templateId),
            level = PermissionLevel(1),
        )

    override fun deleteByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ) {
    }

    companion object {
        private const val PERMISSION_ID_RETURN_NULL = 100L
    }
}
