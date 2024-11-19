package kr.co.shoppingcart.cart.domain.basket.service

import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import org.springframework.stereotype.Component

@Component
class BasketCreationService(
    private val basketRepository: BasketRepository,
) {
    fun save(basket: Basket): Basket = basketRepository.save(basket)
}
