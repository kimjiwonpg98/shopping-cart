package kr.co.shoppingcart.cart.domain.category.services

import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.category.CategoryRepository
import kr.co.shoppingcart.cart.domain.category.vo.Category
import org.springframework.stereotype.Component

@Component
class GetCategoryService(
    private val categoryRepository: CategoryRepository,
    private val basketRepository: BasketRepository,
) {
    fun getAll(): List<Category> = categoryRepository.getAll()

    fun getById(id: Long): Category? = categoryRepository.getById(id)

    fun getByTemplateId(templateId: Long): List<Category> = categoryRepository.getByTemplateId(templateId)

    fun getByTemplateIdV2(templateId: Long): List<String> = basketRepository.getCategoriesByTemplateId(templateId)

    fun getByNameOrBasic(name: String): Category = categoryRepository.getByNameOrBasic(name)
}
