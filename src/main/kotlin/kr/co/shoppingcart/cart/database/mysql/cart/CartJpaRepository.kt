package kr.co.shoppingcart.cart.database.mysql.cart

import kr.co.shoppingcart.cart.database.mysql.cart.entity.CartEntity
import org.springframework.stereotype.Repository

@Repository
interface CartJpaRepository: CartEntityRepository<CartEntity, Long> {
    override fun save(cartEntity: CartEntity): CartEntity
}