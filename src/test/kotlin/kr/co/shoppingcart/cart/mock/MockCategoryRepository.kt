package kr.co.shoppingcart.cart.mock

import kr.co.shoppingcart.cart.domain.category.CategoryRepository
import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.mock.vo.MockCategory

class MockCategoryRepository : CategoryRepository {
    override fun getAll(): List<Category> = MockCategory.getCategories()

    override fun getById(id: Long): Category? = MockCategory.getCategory(1)
}
