package kr.co.shoppingcart.cart.domain.template.services

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import kr.co.shoppingcart.cart.domain.template.command.GetTemplateByIdAndUserIdCommand
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import org.springframework.stereotype.Component

@Component
class GetTemplateService(
    private val templateRepository: TemplateRepository,
) {
    fun getTemplateByIdWithPercentOrFail(
        getTemplateByIdAndUserIdCommand: GetTemplateByIdAndUserIdCommand,
    ): TemplateWithCheckedCount =
        templateRepository.getByIdWithPercent(
            getTemplateByIdAndUserIdCommand.id,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

    fun getByIdOrFail(id: Long): Template =
        templateRepository.getById(id) ?: throw CustomException.responseBody(ExceptionCode.E_404_001)

    fun getWithCompletePercentAndPreview(userId: Long): List<TemplateWithCheckedCount> =
        templateRepository.getWithCompletePercent(userId)
}
