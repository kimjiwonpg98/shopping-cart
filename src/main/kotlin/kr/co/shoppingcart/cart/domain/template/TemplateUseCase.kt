package kr.co.shoppingcart.cart.domain.template

import jakarta.transaction.Transactional
import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.template.command.CopyOwnTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateInCompleteCommand
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.DeleteByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetTemplateByIdAndUserIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetWithCompletePercentAndPreviewCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateSharedByIdCommand
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
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
    fun copyOwnTemplateInComplete(copyTemplateInCompleteCommand: CopyTemplateInCompleteCommand) {
        val template =
            templateRepository.getByIdAndUserId(
                copyTemplateInCompleteCommand.id,
                copyTemplateInCompleteCommand.userId,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        val newTemplate = templateRepository.create(name = template.name.name, userId = template.userId.userId)

        val baskets = basketRepository.getByTemplateId(copyTemplateInCompleteCommand.id)
        if (baskets.isEmpty()) return

        val (checkedItems, nonCheckedItems) = baskets.partition { it.checked.checked }

        this.createNewBasketsByTemplateId(nonCheckedItems, newTemplate.id.id)
    }

    @Transactional
    fun copyOwnTemplate(copyOwnTemplateCommand: CopyOwnTemplateCommand) {
        val template =
            templateRepository.getByIdAndUserId(
                copyOwnTemplateCommand.id,
                copyOwnTemplateCommand.userId,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        val newTemplate = templateRepository.create(name = template.name.name, userId = template.userId.userId)

        val baskets = basketRepository.getByTemplateId(copyOwnTemplateCommand.id)
        if (baskets.isEmpty()) return

        this.createNewBasketsByTemplateId(baskets, newTemplate.id.id)
    }

    @Transactional
    fun copyTemplate(copyTemplateCommand: CopyTemplateCommand) {
        val template =
            templateRepository.getById(
                copyTemplateCommand.id,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_404_001)

        if (!isPublicTemplate(template)) throw CustomException.responseBody(ExceptionCode.E_403_001)

        val newTemplate = templateRepository.create(name = template.name.name, userId = template.userId.userId)

        val baskets = basketRepository.getByTemplateId(copyTemplateCommand.id)
        if (baskets.isEmpty()) return

        this.createNewBasketsByTemplateId(baskets, newTemplate.id.id)
    }

    @Transactional(Transactional.TxType.MANDATORY)
    fun createNewBasketsByTemplateId(
        baskets: List<Basket>,
        templateId: Long,
    ) {
        baskets.map { it.templateId!!.templateId = templateId }
        basketRepository.bulkSave(baskets)
    }

    fun getWithCompletePercentAndPreview(
        getWithCompletePercentAndPreviewCommand: GetWithCompletePercentAndPreviewCommand,
    ): List<TemplateWithCheckedCount> =
        templateRepository.getWithCompletePercent(
            getWithCompletePercentAndPreviewCommand.userId,
            getWithCompletePercentAndPreviewCommand.page,
            getWithCompletePercentAndPreviewCommand.size,
        )

    fun deleteByIdAndUserId(deleteByTemplateIdCommand: DeleteByTemplateIdCommand) {
        val isOwner =
            this.checkedOwnerByUserIdAndId(
                deleteByTemplateIdCommand.userId,
                deleteByTemplateIdCommand.templateId,
            )

        if (!isOwner) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }

        templateRepository.deleteById(deleteByTemplateIdCommand.templateId)
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

    private fun isPublicTemplate(template: Template): Boolean = template.isPublic.isPublic
}
