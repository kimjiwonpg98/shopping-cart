package kr.co.shoppingcart.cart.database.mysql.template.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.co.shoppingcart.cart.database.mysql.common.entity.CommonEntity
import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity.Companion.TEMPLATE_ENTITY_NAME

@Table(name = TEMPLATE_ENTITY_NAME)
@Entity
class TemplateEntity(
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", name = "id")
    val id: Long? = null,
    @Column(nullable = false, length = 200)
    var name: String,
    @Column(columnDefinition = "BIGINT", nullable = false, name = "user_id")
    val userId: Long,
    @Column(columnDefinition = "BOOLEAN", nullable = false, name = "is_public")
    var isPublic: Boolean = false,
    @Column(nullable = false, columnDefinition = "INT", name = "thumbnail_idx")
    var thumbnailIdx: Int = 1,
) : CommonEntity() {
    companion object {
        const val TEMPLATE_ENTITY_NAME = "template"
    }
}
