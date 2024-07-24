package kr.co.shoppingcart.cart.database.mysql.basket

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface BasketEntityRepository<T, ID> : Repository<T, ID> {
    fun save(basketEntity: BasketEntity): BasketEntity

    fun getByTemplateIdOrderByUpdatedAtDesc(templateId: Long): List<BasketEntity>

    fun findByTemplateId(
        templateId: Long,
        pageable: Pageable,
    ): List<BasketEntity>

    fun getByTemplateIdAndCategoryId(
        templateId: Long,
        categoryId: Long,
    ): List<BasketEntity>

    fun getById(id: Long): BasketEntity?
}
