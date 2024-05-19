package kr.co.shoppingcart.cart.api.cart

import jakarta.validation.Valid
import kr.co.shoppingcart.cart.api.cart.dto.CreateCartRequestBodyDto
import kr.co.shoppingcart.cart.domain.cart.CartUseCase
import kr.co.shoppingcart.cart.domain.cart.command.CreateCartCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class CartController (
    private val cartUseCase: CartUseCase
) {
    @PostMapping("/cart")
    fun saveCart(
        @Valid @RequestBody body: CreateCartRequestBodyDto
    ): ResponseEntity<Long> {
        val cart = CreateCartCommand(cartName = body.name, userId = 1)
        cartUseCase.saveCart(cart)
        return ResponseEntity.status(200).body(1)
    }
}