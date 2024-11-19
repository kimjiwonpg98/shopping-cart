package kr.co.shoppingcart.cart.domain.basket.service

import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import org.springframework.stereotype.Component

@Component
class BasketUpdateService(
    private val basketRepository: BasketRepository,
) {
    fun deleteById(basketId: Long) = basketRepository.deleteById(basketId)

    fun updateContent(
        basketId: Long,
        content: String?,
        count: Long?,
    ): Basket =
        this.basketRepository.updateContent(
            basketId,
            content,
            count,
        )

    fun updateCheckedById(
        basketId: Long,
        checked: Boolean,
    ): Basket = this.basketRepository.updateCheckedById(basketId, checked)
}
