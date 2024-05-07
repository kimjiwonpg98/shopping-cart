package kr.co.shoppingcart.cart.domain.cart

import org.springframework.stereotype.Service

@Service
class CartUseCase (
    private val cartRepository: CartRepository,
) {
    fun saveCart(cart: Cart) = cartRepository.createCart(
        name = cart.name.name,
        userId = cart.userId.userId
    )
}