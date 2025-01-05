package kr.co.shoppingcart.cart.domain.permissions

import kr.co.shoppingcart.cart.domain.permissions.vo.Permission

interface PermissionsRepository {
    fun getByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permission?

    fun getByUserId(userId: Long): List<Permission>

    fun create(permission: Permission): Permission

    fun createPermissionByLevel(
        userId: Long,
        templateId: Long,
        level: Int,
    ): Permission

    fun deleteByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    )

    fun deleteByIds(ids: List<Long>)
}
