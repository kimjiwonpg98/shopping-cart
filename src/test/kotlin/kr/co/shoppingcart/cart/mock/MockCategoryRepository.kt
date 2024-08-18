package kr.co.shoppingcart.cart.mock

import kr.co.shoppingcart.cart.domain.category.CategoryRepository
import kr.co.shoppingcart.cart.domain.category.vo.Category

class MockCategoryRepository : CategoryRepository {
    override fun getAll(): List<Category> {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): Category? {
        TODO("Not yet implemented")
    }
}
