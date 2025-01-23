package kr.co.shoppingcart.cart.database.mysql.basket

import kr.co.shoppingcart.cart.database.mysql.basket.entity.BasketEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BasketJpaRepository : BasketEntityRepository<BasketEntity, Long> {
    override fun save(basketEntity: BasketEntity): BasketEntity

    @Query(
        "SELECT b, c FROM BasketEntity b" +
            " JOIN FETCH b.category c" +
            " WHERE b.template.id = :templateId ORDER BY b.checked ASC, b.updatedAt DESC",
    )
    override fun getByTemplateIdOrderByCheckedAscUpdatedAtDesc(
        @Param("templateId") templateId: Long,
    ): List<BasketEntity>

    @Query(
        "SELECT b, c FROM BasketEntity b" +
            " JOIN FETCH b.category c" +
            " WHERE b.template.id = :templateId" +
            " AND b.category.id = :categoryId" +
            " ORDER BY b.checked ASC, b.updatedAt DESC",
    )
    override fun getByTemplateIdAndCategoryIdOrderByUpdatedAtDescCheckedAsc(
        @Param("templateId") templateId: Long,
        @Param("categoryId") categoryId: Long,
    ): List<BasketEntity>

    override fun findByTemplateId(
        templateId: Long,
        pageable: Pageable,
    ): List<BasketEntity>

    override fun findById(id: Long): BasketEntity?

    override fun findByIdIn(ids: List<Long>): List<BasketEntity>

    override fun deleteById(id: Long)

    override fun deleteByIdIn(ids: List<Long>)

    @Modifying
    @Query(
        "UPDATE BasketEntity b SET b.checked = :checked WHERE b.id IN :basketIds",
    )
    override fun updateCheckedByIdIn(
        basketIds: List<Long>,
        checked: Boolean,
    )
}
