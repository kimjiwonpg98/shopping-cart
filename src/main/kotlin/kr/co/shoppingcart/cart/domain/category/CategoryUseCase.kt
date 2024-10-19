package kr.co.shoppingcart.cart.domain.category

import kr.co.shoppingcart.cart.domain.category.vo.Category
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryUseCase(
    private val categoryRepository: CategoryRepository,
) {
    @Transactional(readOnly = true)
    fun getAll(): List<Category> = categoryRepository.getAll()

    @Transactional(readOnly = true)
    fun getById(id: Long): Category? = categoryRepository.getById(id)
}
