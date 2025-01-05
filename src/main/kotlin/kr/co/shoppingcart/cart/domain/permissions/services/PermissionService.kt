package kr.co.shoppingcart.cart.domain.permissions.services

import kr.co.shoppingcart.cart.domain.permissions.vo.Permission

interface PermissionService {
    fun createPermission(
        userId: Long,
        templateId: Long,
    ): Permission

    fun getOverLevelByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permission?

    fun getByUserIdAndTemplateId(
        userId: Long,
        templateId: Long,
    ): Permission?

    fun deleteByIds(ids: List<Long>)

    fun getByUserId(userId: Long): List<Permission>
}
