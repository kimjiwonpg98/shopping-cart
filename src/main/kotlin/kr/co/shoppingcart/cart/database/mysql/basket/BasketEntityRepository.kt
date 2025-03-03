package kr.co.shoppingcart.cart.database.mysql.basket

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface BasketEntityRepository<T, ID> : Repository<T, ID> {
    fun save(basketEntity: BasketEntity): BasketEntity

    fun getByTemplateIdOrderByCheckedAscUpdatedAtDesc(templateId: Long): List<BasketEntity>

    fun getByTemplateIdOrderByUpdatedAtDesc(
        templateId: Long,
        pageable: Pageable,
    ): List<BasketEntity>

    fun findByTemplateId(
        templateId: Long,
        pageable: Pageable,
    ): List<BasketEntity>

    fun getByTemplateIdAndCategoryIdOrderByUpdatedAtDescCheckedAsc(
        templateId: Long,
        categoryId: Long,
    ): List<BasketEntity>

    fun getByTemplateIdAndCategoryNameOrderByUpdatedAtDescCheckedAsc(
        templateId: Long,
        categoryName: String,
    ): List<BasketEntity>

    fun findById(id: Long): BasketEntity?

    fun findByIdIn(ids: List<Long>): List<BasketEntity>

    fun deleteById(id: Long)

    fun deleteByIdIn(ids: List<Long>)

    fun updateCheckedByIdIn(
        basketIds: List<Long>,
        checked: Boolean,
    )

    fun getCategoriesByTemplateId(templateId: Long): List<String>

    fun updateCategoryNameByIdIn(
        basketIds: List<Long>,
        categoryName: String,
    )
}
