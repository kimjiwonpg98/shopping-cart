package kr.co.shoppingcart.cart.core.permission.application.port.input

import kr.co.shoppingcart.cart.core.permission.domain.PermissionLevel

interface ValidPermission {
    fun validate(command: ValidPermissionCommand)

    fun isOverLevel(command: ValidPermissionIsOverLevelCommand)
}

data class ValidPermissionCommand(
    val userId: Long,
    val templateId: Long,
    val level: PermissionLevel,
)

data class ValidPermissionIsOverLevelCommand(
    val userId: Long,
    val templateId: Long,
    val level: PermissionLevel,
)
