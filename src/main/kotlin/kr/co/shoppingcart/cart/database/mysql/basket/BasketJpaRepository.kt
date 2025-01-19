package kr.co.shoppingcart.cart.database.mysql.basket

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BasketJpaRepository : BasketEntityRepository<BasketEntity, Long> {
    override fun save(basketEntity: BasketEntity): BasketEntity

    override fun getByTemplateIdOrderByUpdatedAtDesc(templateId: Long): List<BasketEntity>

    override fun getByTemplateIdAndCategoryIdOrderByUpdatedAtDescCheckedAsc(
        templateId: Long,
        categoryId: Long,
    ): List<BasketEntity>

    override fun findByTemplateId(
        templateId: Long,
        pageable: Pageable,
    ): List<BasketEntity>

    override fun findById(id: Long): BasketEntity?

    override fun deleteById(id: Long)

    @Modifying
    @Query(
        "UPDATE BasketEntity b SET b.checked = :checked WHERE b.id IN :basketIds",
    )
    override fun updateCheckedByIdIn(
        basketIds: List<Long>,
        checked: Boolean,
    )
}
