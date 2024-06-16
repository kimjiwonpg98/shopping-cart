package kr.co.shoppingcart.cart.database.mysql.template

import jakarta.transaction.Transactional
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import org.springframework.stereotype.Component

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
}