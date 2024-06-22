package kr.co.shoppingcart.cart.api.basket

import kr.co.shoppingcart.cart.api.basket.dto.CreateBasketReqBodyDto
import kr.co.shoppingcart.cart.auth.JwtPayload
import kr.co.shoppingcart.cart.auth.annotation.CurrentUser
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BasketController (
    private val basketUseCase: BasketUseCase
) {
    @PostMapping("/basket")
    fun save(
        @RequestBody body: CreateBasketReqBodyDto,
        @CurrentUser currentUser: JwtPayload
    ): ResponseEntity<Unit> {
        val cart = CreateBasketCommand(
            templatedId = body.templateId,
            name = body.name,
            categoryId = body.categoryId,
            count = body.count,
            userId = currentUser.identificationValue.toLong()
        )
        basketUseCase.create(cart)
        return ResponseEntity.status(201).build()
    }
}