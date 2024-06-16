package kr.co.shoppingcart.cart.database.mysql.template

import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import org.springframework.data.repository.NoRepositoryBean
import org.springframework.data.repository.Repository

@NoRepositoryBean
interface TemplateEntityRepository<T, ID>: Repository<T, ID> {
    fun save(templateEntity: TemplateEntity): TemplateEntity
}