package kr.co.shoppingcart.cart.database.mysql.basket.entity

import jakarta.persistence.*
import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity.Companion.BASKET_ENTITY_NAME
import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity
import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import kr.co.shoppingcart.cart.database.mysql.common.entity.CommonEntity

@Table(name = BASKET_ENTITY_NAME)
@Entity
class BasketEntity (
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", name = "id")
    private val id: Long? = null,

    @Column(nullable = false)
    private val content: String,

    @Column(nullable = false, columnDefinition = "INT")
    private val count: Long,

    @Column(nullable = false, columnDefinition = "BOOLEAN", name = "is_completed")
    private val isCompleted: Boolean = false,

    @Column(nullable = false, columnDefinition = "BOOLEAN", name = "is_pinned")
    private val isPinned: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", referencedColumnName = "id", nullable = false)
    private val template: TemplateEntity,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    private val category: CategoryEntity,
    ) : CommonEntity() {
    companion object {
        const val BASKET_ENTITY_NAME = "basket"
    }
}