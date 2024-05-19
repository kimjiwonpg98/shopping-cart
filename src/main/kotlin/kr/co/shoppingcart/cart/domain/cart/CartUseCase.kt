package kr.co.shoppingcart.cart.domain.cart

import kr.co.shoppingcart.cart.domain.cart.command.CreateCartCommand
import org.springframework.stereotype.Service

@Service
class CartUseCase (
    private val cartRepository: CartRepository,
) {
    fun saveCart(cart: CreateCartCommand) = cartRepository.createCart(
        name = cart.cartName,
        userId = cart.userId
    )
}