package kr.co.shoppingcart.cart.domain.category

import kr.co.shoppingcart.cart.domain.category.services.GetCategoryService
import kr.co.shoppingcart.cart.domain.category.vo.Category
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoryUseCase(
    private val getCategoryService: GetCategoryService,
) {
    @Transactional(readOnly = true)
    fun getAll(): List<Category> = getCategoryService.getAll()

    @Transactional(readOnly = true)
    fun getById(id: Long): Category? = getCategoryService.getById(id)

    @Transactional(readOnly = true)
    fun getByTemplateId(templateId: Long): List<Category> = getCategoryService.getByTemplateId(templateId)

    @Transactional(readOnly = true)
    fun getByTemplateIdV2(templateId: Long): List<String> = getCategoryService.getByTemplateIdV2(templateId)
}
