package kr.co.shoppingcart.cart.core.permission.application.port.input

interface DeletePermission {
    fun delete(command: DeletePermissionCommand)

    fun deleteByIds(command: DeletePermissionByIdsCommand)
}

data class DeletePermissionByIdsCommand(
    val ids: List<Long>,
)

data class DeletePermissionCommand(
    val userId: Long,
    val templateId: Long,
)
