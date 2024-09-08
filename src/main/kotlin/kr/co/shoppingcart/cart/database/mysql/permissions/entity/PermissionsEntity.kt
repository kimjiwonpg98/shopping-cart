package kr.co.shoppingcart.cart.database.mysql.permissions.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import kr.co.shoppingcart.cart.database.mysql.common.entity.CommonEntity
import kr.co.shoppingcart.cart.database.mysql.permissions.entity.PermissionsEntity.Companion.PERMISSIONS_ENTITY_NAME

@Table(
    name = PERMISSIONS_ENTITY_NAME,
    uniqueConstraints = [UniqueConstraint(columnNames = ["user_id", "template_id", "level"])],
)
@Entity
data class PermissionsEntity(
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", name = "id")
    val id: Long? = null,
    @Column(nullable = false, length = 3)
    var level: Int,
    @Column(columnDefinition = "BIGINT", name = "user_id")
    val userId: Long,
    @Column(columnDefinition = "BIGINT", name = "template_id")
    val templateId: Long,
) : CommonEntity() {
    companion object {
        const val PERMISSIONS_ENTITY_NAME = "permissions"
    }
}
