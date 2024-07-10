package kr.co.shoppingcart.cart.domain.template

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.GetTemplateByIdAndUserIdCommand
import org.springframework.stereotype.Service

@Service
class TemplateUseCase (
    private val templateRepository: TemplateRepository,
) {
    fun save(createTemplateCommand: CreateTemplateCommand) = templateRepository.create(
        name = createTemplateCommand.name,
        userId = createTemplateCommand.userId
    )

    fun getByIdAndUserId(getTemplateByIdAndUserIdCommand: GetTemplateByIdAndUserIdCommand) =
        templateRepository.getByIdAndUserId(
            getTemplateByIdAndUserIdCommand.id.toLong(),
            getTemplateByIdAndUserIdCommand.userId
        ) ?: throw CustomException.responseBody(ExceptionCode.E_401_000)
}