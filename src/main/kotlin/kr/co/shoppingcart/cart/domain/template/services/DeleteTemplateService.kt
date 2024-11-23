package kr.co.shoppingcart.cart.domain.template.services

import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class DeleteTemplateService(
    private val templateRepository: TemplateRepository,
) {
    fun deleteById(templateId: Long) {
        templateRepository.deleteById(templateId)
    }

    @Transactional(propagation = Propagation.MANDATORY)
    fun deleteByIds(templateIds: List<Long>) {
        templateRepository.deleteByIds(templateIds)
    }
}
