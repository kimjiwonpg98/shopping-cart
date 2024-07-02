package kr.co.shoppingcart.cart.api.basket

import kr.co.shoppingcart.cart.api.basket.dto.CheckedBasketReqBodyDto
import kr.co.shoppingcart.cart.api.basket.dto.CreateBasketReqBodyDto
import kr.co.shoppingcart.cart.auth.JwtPayload
import kr.co.shoppingcart.cart.auth.annotation.CurrentUser
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
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
        val basket = CreateBasketCommand(
            templatedId = body.templateId,
            name = body.name,
            categoryId = body.categoryId,
            count = body.count,
            userId = currentUser.identificationValue.toLong()
        )
        basketUseCase.create(basket)
        return ResponseEntity.status(201).build()
    }

    @PatchMapping("/basket/check")
    fun check(
        @RequestBody body: CheckedBasketReqBodyDto,
        @CurrentUser currentUser: JwtPayload
    ): ResponseEntity<Unit> {
        val basket = UpdateBasketFlagCommand(
            userId = currentUser.identificationValue.toLong(),
            basketId = body.basketId,
            checked = body.checked
        )

        basketUseCase.updateIsAddedByFlagAndId(basket)
        return ResponseEntity.status(200).build()
    }
}