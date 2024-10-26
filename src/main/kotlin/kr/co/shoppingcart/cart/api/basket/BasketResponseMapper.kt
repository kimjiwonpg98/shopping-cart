package kr.co.shoppingcart.cart.api.basket

import kr.co.shoppingcart.cart.api.basket.dto.response.BasketResponse
import kr.co.shoppingcart.cart.api.category.response.CategoryResponse
import kr.co.shoppingcart.cart.domain.basket.vo.Basket

object BasketResponseMapper {
    fun toResponse(basket: Basket): BasketResponse =
        BasketResponse(
            id = basket.id.id,
            name = basket.name.name,
            count = basket.count.count,
            checked = basket.checked.checked,
            createTime = basket.createTime!!.createdAt,
            updateTime = basket.updateTime!!.updatedAt,
            category = CategoryResponse.toDomain(basket.category),
        )
}
