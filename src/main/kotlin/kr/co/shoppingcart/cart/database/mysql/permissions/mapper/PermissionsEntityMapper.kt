package kr.co.shoppingcart.cart.database.mysql.permissions.mapper

import kr.co.shoppingcart.cart.database.mysql.permissions.entity.PermissionsEntity
import kr.co.shoppingcart.cart.domain.permissions.vo.Permission

object PermissionsEntityMapper {
    fun toDomain(permissionsEntity: PermissionsEntity): Permission =
        Permission.toDomain(
            id = permissionsEntity.id!!,
            templateId = permissionsEntity.templateId,
            userId = permissionsEntity.userId,
            level = permissionsEntity.level,
        )

    fun toEntity(permission: Permission): PermissionsEntity =
        PermissionsEntity(
            userId = permission.userId.userId,
            level = permission.level.level,
            templateId = permission.templateId.templateId,
        )
}
