package kr.co.shoppingcart.cart.database.mysql.template

import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TemplateJpaRepository : TemplateEntityRepository<TemplateEntity, Long> {
    override fun save(templateEntity: TemplateEntity): TemplateEntity

    override fun findById(id: Long): TemplateEntity?

    override fun getById(id: Long): TemplateEntity

    override fun getByIdAndUserId(
        id: Long,
        userId: Long,
    ): TemplateEntity?

    override fun getByUserId(userId: Long): List<TemplateEntity>

    @Query(
        "SELECT b, t FROM TemplateEntity t " +
            "INNER JOIN BasketEntity b ON t.id = b.template.id" +
            " WHERE t.id = :id",
    )
    override fun getByIdWithBasket(
        @Param("id") id: Long,
    ): TemplateEntity?

    override fun deleteById(id: Long)

    override fun deleteByIdIn(ids: List<Long>)

    @Query(
        value =
            "SELECT t.* FROM template t " +
                " WHERE t.user_id = :userId AND t.name REGEXP '^장바구니 [0-9]+\$' ORDER BY t.id DESC LIMIT 1",
        nativeQuery = true,
    )
    override fun getByUserIdForDefaultName(
        @Param("userId") userId: Long,
    ): TemplateEntity?

    override fun countByUserId(userId: Long): Long
}
