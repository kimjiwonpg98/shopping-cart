package kr.co.shoppingcart.cart.database.mysql.template

import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface TemplateJpaRepository: TemplateEntityRepository<TemplateEntity, Long> {
    override fun save(templateEntity: TemplateEntity): TemplateEntity
    override fun getById(id: Long): TemplateEntity?
    override fun getByIdAndUserId(id: Long, userId: Long): TemplateEntity?

    @Query("SELECT b, t FROM TemplateEntity t " +
            "INNER JOIN BasketEntity b ON t.id = b.template.id" +
            " WHERE t.id = :id")
    override fun getByIdWithBasket(@Param("id") id: Long): TemplateEntity?
}