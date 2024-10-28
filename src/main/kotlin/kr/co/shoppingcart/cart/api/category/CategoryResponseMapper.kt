package kr.co.shoppingcart.cart.api.category

import kr.co.shoppingcart.cart.api.category.response.CategoryResponse
import kr.co.shoppingcart.cart.domain.category.vo.Category

object CategoryResponseMapper {
    fun toResponse(category: Category): CategoryResponse =
        CategoryResponse(
            id = category.id.id,
            name = category.name.name,
        )
}
