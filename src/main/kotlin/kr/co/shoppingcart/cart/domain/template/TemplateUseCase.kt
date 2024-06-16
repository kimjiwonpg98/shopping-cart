package kr.co.shoppingcart.cart.domain.template

import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import org.springframework.stereotype.Service

@Service
class TemplateUseCase (
    private val templateRepository: TemplateRepository,
) {
    fun save(template: CreateTemplateCommand) = templateRepository.create(
        name = template.name,
        userId = template.userId
    )
}