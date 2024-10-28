package kr.co.shoppingcart.cart.database.mysql.category

import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity
import kr.co.shoppingcart.cart.database.mysql.category.jdbc.CategoryJdbcRepository
import kr.co.shoppingcart.cart.domain.category.CategoryRepository
import kr.co.shoppingcart.cart.domain.category.vo.Category
import org.springframework.stereotype.Component

@Component
class CategoryRepositoryAdapter(
    private val categoryEntityRepository: CategoryEntityRepository<CategoryEntity, Long>,
    private val categoryJdbcRepository: CategoryJdbcRepository,
) : CategoryRepository {
    override fun getAll(): List<Category> =
        categoryEntityRepository.findAll().map {
            Category.toDomain(it.id!!, it.name)
        }

    override fun getById(id: Long): Category? =
        categoryEntityRepository.getById(id)?.let {
            return Category.toDomain(it.id!!, it.name)
        }

    override fun getByTemplateId(templateId: Long): List<Category> {
        val category = categoryJdbcRepository.getByTemplateId(templateId)
        return category.map { Category.toDomain(it.categoryId, it.categoryName) }
    }
}
