package kr.co.shoppingcart.cart.domain.category

import kr.co.shoppingcart.cart.domain.category.vo.Category

interface CategoryRepository {
    fun getAll(): List<Category>

    fun getById(id: Long): Category?
}
