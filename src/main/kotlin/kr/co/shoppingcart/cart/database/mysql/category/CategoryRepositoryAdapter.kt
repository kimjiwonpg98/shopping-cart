package kr.co.shoppingcart.cart.database.mysql.category

import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity
import kr.co.shoppingcart.cart.database.mysql.category.mapper.CategoryEntityMapper
import kr.co.shoppingcart.cart.domain.category.CategoryRepository
import kr.co.shoppingcart.cart.domain.category.vo.Category
import org.springframework.stereotype.Component

@Component
class CategoryRepositoryAdapter(
    private val categoryEntityRepository: CategoryEntityRepository<CategoryEntity, Long>,
) : CategoryRepository {
    override fun getAll(): List<Category> = categoryEntityRepository.findAll().map(CategoryEntityMapper::toDomain)

    override fun getById(id: Long): Category? =
        categoryEntityRepository.getById(id)?.let(CategoryEntityMapper::toDomain)

    override fun getByNameOrBasic(name: String): Category =
        categoryEntityRepository.findByName(name)?.let(CategoryEntityMapper::toDomain)
            ?: categoryEntityRepository.getById(1L)!!.let(CategoryEntityMapper::toDomain)

    override fun getByTemplateId(templateId: Long): List<Category> {
        val category = categoryEntityRepository.getByTemplateId(templateId)
        return category.map(CategoryEntityMapper::toDomain)
    }
}
