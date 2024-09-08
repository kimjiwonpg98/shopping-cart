package kr.co.shoppingcart.cart.domain.permissions

import kr.co.shoppingcart.cart.domain.permissions.vo.Permissions

interface PermissionsRepository {
    fun getByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permissions?

    fun create(permission: Permissions): Permissions

    fun createOwnerPermission(
        userId: Long,
        templateId: Long,
    ): Permissions

    fun createWriterPermission(
        userId: Long,
        templateId: Long,
    ): Permissions

    fun deleteByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    )
}
