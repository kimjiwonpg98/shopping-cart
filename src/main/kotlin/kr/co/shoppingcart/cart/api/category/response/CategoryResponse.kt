package kr.co.shoppingcart.cart.api.category.response

import kr.co.shoppingcart.cart.domain.category.vo.Category

data class CategoryResponse(
    val name: String,
) {
    companion object {
        fun toDomain(category: Category): CategoryResponse =
            CategoryResponse(
                name = category.name.name,
            )
    }
}
