package kr.co.shoppingcart.cart.core.permission.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import kr.co.shoppingcart.cart.utils.BaseEntity

@Table(
    name = "permissions",
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "template_id", "level"])],
)
@Entity(name = "permissions")
class Permission(
    @Column(nullable = false, length = 3)
    @Enumerated(EnumType.ORDINAL)
    var level: PermissionLevel,
    @Column(columnDefinition = "BIGINT", name = "user_id")
    val userId: Long,
    @Column(columnDefinition = "BIGINT", name = "template_id")
    val templateId: Long,
) : BaseEntity() {
    companion object {
        fun create(
            userId: Long,
            templateId: Long,
            level: PermissionLevel,
        ) = Permission(level, userId, templateId)
    }
}
