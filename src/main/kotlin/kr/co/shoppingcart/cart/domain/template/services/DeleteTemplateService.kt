package kr.co.shoppingcart.cart.domain.template.services

import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import org.springframework.stereotype.Component

@Component
class DeleteTemplateService(
    private val templateRepository: TemplateRepository,
) {
    fun deleteById(templateId: Long) {
        templateRepository.deleteById(templateId)
    }
}
