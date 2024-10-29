package kr.co.shoppingcart.cart.database.mysql.category

import kr.co.shoppingcart.cart.database.mysql.category.entity.CategoryEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface CategoryJpaRepository : CategoryEntityRepository<CategoryEntity, Long> {
    override fun save(categoryEntity: CategoryEntity): CategoryEntity

    override fun getById(id: Long): CategoryEntity?

    override fun findAll(): List<CategoryEntity>

    @Query(
        "SELECT DISTINCT c " +
            "FROM BasketEntity AS b " +
            "INNER JOIN b.category AS c " +
            "WHERE b.template.id = :templateId",
    )
    override fun getByTemplateId(
        @Param("templateId") templateId: Long,
    ): List<CategoryEntity>
}
