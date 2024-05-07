package kr.co.shoppingcart.cart.database.mysql.cart

import kr.co.shoppingcart.cart.database.mysql.cart.CartEntityRepository
import kr.co.shoppingcart.cart.database.mysql.cart.entity.CartEntity
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CartJpaRepository: CartEntityRepository<CartEntity, Long> {
    @Modifying
    @Query(value = "INSERT INTO CartEntity(name, user_id) VALUES (:name, :userId)", nativeQuery = true)
    override fun saveCart(@Param("name") name: String, @Param("userId") userId: Long)
}