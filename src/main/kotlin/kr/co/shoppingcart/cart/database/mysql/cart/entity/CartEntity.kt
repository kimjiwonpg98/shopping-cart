package kr.co.shoppingcart.cart.database.mysql.cart.entity

import jakarta.persistence.*
import kr.co.shoppingcart.cart.database.mysql.cart.entity.CartEntity.Companion.CART_ENTITY_NAME

@Table(name = CART_ENTITY_NAME)
@Entity
class CartEntity (
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT", name = "id")
    private val id: Long,

    @Column(nullable = false, length = 200)
    private val name: String,

    @Column(columnDefinition = "BIGINT", nullable = false, name = "user_id")
    private val userId: Long?,
) {
    companion object {
        const val CART_ENTITY_NAME = "cart"
    }
}