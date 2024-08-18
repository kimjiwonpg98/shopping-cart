package kr.co.shoppingcart.cart.mock.vo

import kr.co.shoppingcart.cart.domain.category.vo.Category

object MockCategory {
    fun getCategory(i: Long): Category = Category.toDomain(i, "test")

    fun getCategories(): List<Category> {
        val categories = mutableListOf<Category>()
        for (i in 0..5) {
            categories.add(getCategory(i.toLong()))
        }
        return categories
    }
}
