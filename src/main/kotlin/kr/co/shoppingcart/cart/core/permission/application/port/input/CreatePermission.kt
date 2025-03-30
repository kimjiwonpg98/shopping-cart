package kr.co.shoppingcart.cart.core.permission.application.port.input

import kr.co.shoppingcart.cart.core.permission.domain.Permission
import kr.co.shoppingcart.cart.core.permission.domain.PermissionLevel

interface CreatePermission {
    fun create(command: CreatePermissionCommand)
}

data class CreatePermissionCommand(
    val userId: Long,
    val templateId: Long,
    val level: PermissionLevel,
) {
    fun of() = Permission.create(userId, templateId, level)
}
