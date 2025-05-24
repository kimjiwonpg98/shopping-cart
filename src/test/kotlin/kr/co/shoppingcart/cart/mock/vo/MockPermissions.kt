package kr.co.shoppingcart.cart.mock.vo

import kr.co.shoppingcart.cart.domain.permissions.vo.Permission
import kr.co.shoppingcart.cart.fixture.TemplateFixture.DEFAULT_USER_ID

object MockPermissions {
    fun getPermission(
        i: Long,
        level: Int,
        userId: Long = DEFAULT_USER_ID,
        templateId: Long = 1,
    ): Permission = Permission.toDomain(i, userId, templateId, level)

    fun getPermissionWriter(
        i: Long,
        flag: Boolean,
    ): Permission = if (!flag) getPermission(i, 0) else getPermission(i, 1)

    fun getOptionalPermission(
        i: Long,
        flag: Boolean,
    ): Permission? = if (flag) getPermissionWriter(i, true) else null

    fun getPermissionReader(i: Long): Permission = Permission.toDomain(i, i, i, 3)
}
