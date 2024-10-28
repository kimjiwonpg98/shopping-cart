package kr.co.shoppingcart.cart.api.category.response

import kr.co.shoppingcart.cart.domain.category.vo.Category

data class CategoryResponse(
    val id: Long,
    val name: String,
) {
    companion object {
        fun toDomain(category: Category): CategoryResponse =
            CategoryResponse(
                id = category.id.id,
                name = category.name.name,
            )
    }
}
