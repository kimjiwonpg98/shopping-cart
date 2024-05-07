package kr.co.shoppingcart.cart.domain.cart

interface CartRepository {
    fun createCart(name: String, userId: Long)
}