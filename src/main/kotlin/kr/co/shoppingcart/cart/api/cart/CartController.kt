package kr.co.shoppingcart.cart.api.cart

import jakarta.validation.Valid
import kr.co.shoppingcart.cart.api.cart.dto.CreateCartRequestBodyDto
import kr.co.shoppingcart.cart.domain.cart.Cart
import kr.co.shoppingcart.cart.domain.cart.CartUseCase
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Controller("/cart")
class CartController (
    private val cartUseCase: CartUseCase
) {
    @PostMapping("/")
    fun saveCart(
        @Valid @RequestBody body: CreateCartRequestBodyDto
    ) {
        val cart = Cart.convertToDomain(id = 1, name = body.name, userId = 2)
        cartUseCase.saveCart(cart)
    }
}