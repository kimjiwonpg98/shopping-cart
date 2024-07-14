package kr.co.shoppingcart.cart.database.mysql.basket.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity.Companion.BASKET_ENTITY_NAME
import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity
import kr.co.shoppingcart.cart.database.mysql.common.entity.CommonEntity
import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity

@Table(name = BASKET_ENTITY_NAME)
@Entity
class BasketEntity(
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", name = "id")
    val id: Long? = null,
    @Column(nullable = false)
    val content: String,
    @Column(nullable = false, columnDefinition = "INT")
    val count: Long = 1L,
    @Column(nullable = false, columnDefinition = "BOOLEAN", name = "checked")
    var checked: Boolean = false,
    @Column(nullable = false, columnDefinition = "BOOLEAN", name = "is_pinned")
    val isPinned: Boolean = false,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", referencedColumnName = "id", nullable = false)
    val template: TemplateEntity,
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    val category: CategoryEntity,
) : CommonEntity() {
    companion object {
        const val BASKET_ENTITY_NAME = "basket"
    }
}
