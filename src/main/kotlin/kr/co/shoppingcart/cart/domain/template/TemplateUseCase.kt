package kr.co.shoppingcart.cart.domain.template

import jakarta.transaction.Transactional
import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateInCompleteCommand
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.GetTemplateByIdAndUserIdCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateSharedByIdCommand
import org.springframework.stereotype.Service

@Service
class TemplateUseCase(
    private val templateRepository: TemplateRepository,
    private val basketRepository: BasketRepository,
) {
    fun save(createTemplateCommand: CreateTemplateCommand) =
        templateRepository.create(
            name = createTemplateCommand.name,
            userId = createTemplateCommand.userId,
        )

    fun getByIdAndUserId(getTemplateByIdAndUserIdCommand: GetTemplateByIdAndUserIdCommand) =
        templateRepository.getByIdAndUserId(
            getTemplateByIdAndUserIdCommand.id.toLong(),
            getTemplateByIdAndUserIdCommand.userId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

    fun updateSharedById(updateTemplateSharedByIdCommand: UpdateTemplateSharedByIdCommand) {
        if (!this.checkedOwnerByUserIdAndId(
                updateTemplateSharedByIdCommand.userId,
                updateTemplateSharedByIdCommand.id,
            )
        ) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }

        templateRepository.updateSharedById(
            updateTemplateSharedByIdCommand.id,
            updateTemplateSharedByIdCommand.isShared,
        )
    }

    @Transactional
    fun copyTemplateInComplete(copyTemplateInCompleteCommand: CopyTemplateInCompleteCommand) {
        val template =
            templateRepository.getByIdAndUserId(
                copyTemplateInCompleteCommand.id,
                copyTemplateInCompleteCommand.userId,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        val basket = basketRepository.getByTemplateId(copyTemplateInCompleteCommand.id)
        val (checkedItems, nonCheckedItems) = basket.partition { it.checked.checked }

        val newTemplate = templateRepository.create(name = template.name.name, userId = template.userId.userId)
        nonCheckedItems.map { it.templateId!!.templateId = newTemplate.id.id }
        basketRepository.bulkSave(nonCheckedItems)
    }

    private fun checkedOwnerByUserIdAndId(
        userId: Long,
        id: Long,
    ): Boolean {
        val template =
            templateRepository.getByIdAndUserId(
                id,
                userId,
            )
        return template != null
    }
}
