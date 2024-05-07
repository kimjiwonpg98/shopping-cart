package kr.co.shoppingcart.cart.database.mysql.cart

import kr.co.shoppingcart.cart.database.mysql.cart.entity.CartEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CartJpaRepository: CartEntityRepository<CartEntity, Long> {
    @Modifying
    @Query(value = "INSERT INTO cart(name, user_id) VALUES (:name, :userId)", nativeQuery = true)
    override fun saveCart(name: String, userId: Long)
}