package kr.co.shoppingcart.cart.database.mysql.template

import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import org.springframework.stereotype.Repository

@Repository
interface TemplateJpaRepository: TemplateEntityRepository<TemplateEntity, Long> {
    override fun save(templateEntity: TemplateEntity): TemplateEntity
    override fun getById(id: Long): TemplateEntity?
    override fun getByIdAndUserId(id: Long, userId: Long): TemplateEntity?
}