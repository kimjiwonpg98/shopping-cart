package kr.co.shoppingcart.cart.database.mysql.template.entity

import jakarta.persistence.*
import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity.Companion.TEMPLATE_ENTITY_NAME
import kr.co.shoppingcart.cart.database.mysql.common.entity.CommonEntity

@Table(name = TEMPLATE_ENTITY_NAME)
@Entity
class TemplateEntity (
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", name = "id")
    val id: Long? = null,

    @Column(nullable = false, length = 200)
    val name: String,

    @Column(columnDefinition = "BIGINT", nullable = false, name = "user_id")
    val userId: Long,

    @Column(columnDefinition = "BOOLEAN", nullable = false, name = "is_public")
    var isPublic: Boolean = false,
): CommonEntity() {
    companion object {
        const val TEMPLATE_ENTITY_NAME = "template"
    }
}