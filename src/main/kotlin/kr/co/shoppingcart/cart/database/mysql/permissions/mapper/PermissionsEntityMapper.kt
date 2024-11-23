package kr.co.shoppingcart.cart.database.mysql.permissions.mapper

import kr.co.shoppingcart.cart.database.mysql.permissions.entity.PermissionsEntity
import kr.co.shoppingcart.cart.domain.permissions.vo.Permissions

object PermissionsEntityMapper {
    fun toDomain(permissionsEntity: PermissionsEntity): Permissions =
        Permissions.toDomain(
            id = permissionsEntity.id!!,
            templateId = permissionsEntity.templateId,
            userId = permissionsEntity.userId,
            level = permissionsEntity.level,
        )

    fun toEntity(permissions: Permissions): PermissionsEntity =
        PermissionsEntity(
            userId = permissions.userId.userId,
            level = permissions.level.level,
            templateId = permissions.templateId.templateId,
        )
}
