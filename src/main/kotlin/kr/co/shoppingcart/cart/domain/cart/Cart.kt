package kr.co.shoppingcart.cart.domain.cart

import kr.co.shoppingcart.cart.domain.cart.vo.CartId
import kr.co.shoppingcart.cart.domain.cart.vo.CartName
import kr.co.shoppingcart.cart.domain.cart.vo.CartOwnerId

data class Cart (
    val id: CartId,
    val name: CartName,
    val userId: CartOwnerId
) {
    companion object {
        fun convertToDomain(id: Long, name: String, userId: Long): Cart =
            Cart(
                id = CartId(id),
                name = CartName(name),
                userId = CartOwnerId(userId)
            )
    }

}