package kr.co.shoppingcart.cart.database.mysql.category

import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface CategoryEntityRepository<T, ID> : Repository<T, ID> {
    fun save(categoryEntity: CategoryEntity): CategoryEntity

    fun getById(id: Long): CategoryEntity?

    fun findAll(): List<CategoryEntity>
}
