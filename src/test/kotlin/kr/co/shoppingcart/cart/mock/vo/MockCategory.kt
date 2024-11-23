package kr.co.shoppingcart.cart.mock.vo

import kr.co.shoppingcart.cart.domain.category.vo.Category

object MockCategory {
    fun getCategory(i: Long): Category = Category.toDomain(i, "기타")

    fun getOptionalCategory(
        i: Long,
        flag: Boolean,
    ): Category? = if (!flag) getCategory(i) else null

    fun getCategories(): List<Category> {
        val categories = mutableListOf<Category>()
        for (i in 0..5) {
            categories.add(getCategory(i.toLong()))
        }
        return categories
    }
}
