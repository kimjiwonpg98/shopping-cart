package kr.co.shoppingcart.cart.database.mysql.category

import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity
import kr.co.shoppingcart.cart.domain.category.CategoryRepository
import kr.co.shoppingcart.cart.domain.category.vo.Category
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class CategoryRepositoryAdapter (
    private val categoryEntityRepository: CategoryEntityRepository<CategoryEntity, Long>
): CategoryRepository {
    @Transactional(readOnly = true)
    override fun getAll(): List<Category> = categoryEntityRepository.findAll().map {
        Category.toDomain(it.id!!, it.name)
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): Category? = categoryEntityRepository.getById(id).let {
            if (it === null) return null
            return Category.toDomain(it.id!!, it.name)
        }

}