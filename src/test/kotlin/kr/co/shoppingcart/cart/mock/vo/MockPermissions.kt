package kr.co.shoppingcart.cart.mock.vo

import kr.co.shoppingcart.cart.domain.permissions.vo.Permissions

object MockPermissions {
    fun getPermission(
        i: Long,
        level: Int,
    ): Permissions = Permissions.toDomain(i, i, level)

    fun getPermissionWriter(
        i: Long,
        flag: Boolean,
    ): Permissions = if (!flag) getPermission(i, 0) else getPermission(i, 2)

    fun getOptionalPermission(
        i: Long,
        flag: Boolean,
    ): Permissions? = if (flag) getPermissionWriter(i, true) else null
}
