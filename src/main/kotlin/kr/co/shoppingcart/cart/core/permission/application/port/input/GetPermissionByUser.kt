package kr.co.shoppingcart.cart.core.permission.application.port.input

import kr.co.shoppingcart.cart.core.permission.domain.Permission

interface GetPermissionByUser {
    fun getByUserIdAndTemplateId(command: GetPermissionByUserIdAndTemplateIdCommand): Permission?

    fun getByUserId(userId: Long): List<Permission>
}

data class GetPermissionByUserIdAndTemplateIdCommand(
    val userId: Long,
    val templateId: Long,
)
