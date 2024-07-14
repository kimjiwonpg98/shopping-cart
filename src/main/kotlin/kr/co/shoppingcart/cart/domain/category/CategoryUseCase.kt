package kr.co.shoppingcart.cart.domain.category

import kr.co.shoppingcart.cart.domain.category.vo.Category
import org.springframework.stereotype.Service

@Service
class CategoryUseCase(
    private val categoryRepository: CategoryRepository,
) {
    fun getAll(): List<Category> = categoryRepository.getAll()

    fun getById(id: Long): Category? = categoryRepository.getById(id)
}
