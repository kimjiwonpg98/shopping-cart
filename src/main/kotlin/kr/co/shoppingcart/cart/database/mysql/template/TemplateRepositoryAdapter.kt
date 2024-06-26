package kr.co.shoppingcart.cart.database.mysql.template

import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import kr.co.shoppingcart.cart.domain.template.vo.Template
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class TemplateRepositoryAdapter(
    private val templateEntityRepository: TemplateEntityRepository<TemplateEntity, Long>
): TemplateRepository {
    @Transactional
    override fun create(name: String, userId: Long) {
        templateEntityRepository.save(
            TemplateEntity(
                name = name,
                userId = userId,
            )
        )
    }

    @Transactional(readOnly = true)
    override fun getById(id: Long): Template? =
        templateEntityRepository.getById(id).let {
            if (it === null) return null
            return Template.toDomain(
                id = it.id!!,
                name = it.name,
                userId = it.userId
            )
        }

    @Transactional(readOnly = true)
    override fun getByIdAndUserId(id: Long, userId: Long): Template? =
        templateEntityRepository.getByIdAndUserId(id, userId).let {
            if (it === null) return null
            return Template.toDomain(
                id = it.id!!,
                name = it.name,
                userId = it.userId
            )
        }

}


