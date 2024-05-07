package kr.co.shoppingcart.cart.database.mysql.cart.entity

import jakarta.persistence.*
import kr.co.shoppingcart.cart.database.mysql.cart.entity.CartEntity.Companion.CART_ENTITY_NAME

@Entity
@Table(name = CART_ENTITY_NAME)
class CartEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT")
    private val id: Long?,

    @Column(columnDefinition = "VARCHAR", nullable = false)
    private val name: String,

    @Column(columnDefinition = "BIGINT", nullable = false, name = "user_id")
    private val userId: Long?,
) {
    companion object {
        const val CART_ENTITY_NAME = "cart"
    }
}