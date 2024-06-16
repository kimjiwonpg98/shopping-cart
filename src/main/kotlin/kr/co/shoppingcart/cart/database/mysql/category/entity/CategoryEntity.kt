package kr.co.shoppingcart.cart.database.mysql.category.entity

import jakarta.persistence.*
import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity.Companion.CATEGORY_ENTITY_NAME
import kr.co.shoppingcart.cart.database.mysql.common.entity.CommonEntity

@Table(name = CATEGORY_ENTITY_NAME)
@Entity
data class CategoryEntity (
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", name = "id")
    private val id: Long? = null,

    @Column(nullable = false, length = 200)
    private val name: String,
): CommonEntity() {
    companion object {
        const val CATEGORY_ENTITY_NAME = "category"
    }
}