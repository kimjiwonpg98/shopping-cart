package kr.co.shoppingcart.cart.database.mysql.cart

import kr.co.shoppingcart.cart.domain.cart.CartRepository
import kr.co.shoppingcart.cart.database.mysql.cart.entity.CartEntity
import org.springframework.stereotype.Component

@Component
class CartRepositoryAdapter(
    private val cartEntityRepository: CartEntityRepository<CartEntity, Long>
): CartRepository {
    override fun createCart(name: String, userId: Long) {
        cartEntityRepository.saveCart(name, userId)
    }
}