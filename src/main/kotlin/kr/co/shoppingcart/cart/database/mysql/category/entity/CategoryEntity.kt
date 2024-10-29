package kr.co.shoppingcart.cart.database.mysql.category.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity.Companion.CATEGORY_ENTITY_NAME
import kr.co.shoppingcart.cart.database.mysql.common.entity.CommonEntity

@Table(name = CATEGORY_ENTITY_NAME)
@Entity
data class CategoryEntity(
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", name = "id")
    val id: Long = 0,
    @Column(nullable = false, length = 200)
    val name: String,
) : CommonEntity() {
    companion object {
        const val CATEGORY_ENTITY_NAME = "category"
    }
}
