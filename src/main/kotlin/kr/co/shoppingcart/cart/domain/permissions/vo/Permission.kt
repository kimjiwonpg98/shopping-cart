package kr.co.shoppingcart.cart.domain.permissions.vo

data class Permission(
    val id: PermissionId,
    val templateId: PermissionTemplateId,
    val userId: PermissionUserId,
    val level: PermissionLevel,
) {
    companion object {
        fun toDomain(
            id: Long,
            userId: Long,
            templateId: Long,
            level: Int,
        ): Permission =
            Permission(
                id = PermissionId(id),
                templateId = PermissionTemplateId(templateId),
                userId = PermissionUserId(userId),
                level = PermissionLevel(level),
            )
    }
}
