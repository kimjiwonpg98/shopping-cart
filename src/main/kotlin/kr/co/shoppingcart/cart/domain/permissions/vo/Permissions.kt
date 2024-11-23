package kr.co.shoppingcart.cart.domain.permissions.vo

data class Permissions(
    val id: PermissionId,
    val templateId: PermissionTemplateId,
    val userId: PermissionUserId,
    val level: PermissionLevel,
) {
    fun checkWritePermissionByLevel(): Boolean = this.level.level <= 2

    companion object {
        fun toDomain(
            id: Long,
            userId: Long,
            templateId: Long,
            level: Int,
        ): Permissions =
            Permissions(
                id = PermissionId(id),
                templateId = PermissionTemplateId(templateId),
                userId = PermissionUserId(userId),
                level = PermissionLevel(level),
            )
    }
}
