package kr.co.shoppingcart.cart.database.mysql.category

import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity
import org.springframework.stereotype.Repository

@Repository
interface CategoryJpaRepository : CategoryEntityRepository<CategoryEntity, Long> {
    override fun save(categoryEntity: CategoryEntity): CategoryEntity

    override fun getById(id: Long): CategoryEntity?

    override fun findAll(): List<CategoryEntity>
}
