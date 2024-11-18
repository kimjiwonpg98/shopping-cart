package kr.co.shoppingcart.cart.domain.template

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.permissions.services.OwnerPermissionService
import kr.co.shoppingcart.cart.domain.template.command.CopyOwnTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateInCompleteCommand
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.DeleteByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetTemplateByIdAndUserIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetWithCompletePercentAndPreviewCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateByIdCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateSharedByIdCommand
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class TemplateUseCase(
    private val templateRepository: TemplateRepository,
    private val basketRepository: BasketRepository,
    private val ownerPermissionService: OwnerPermissionService,
) {
    @Transactional
    fun createByApi(createTemplateCommand: CreateTemplateCommand): Template =
        this.create(createTemplateCommand.name, createTemplateCommand.userId)

    @Transactional(readOnly = true)
    fun getByIdAndUserIdToRead(
        getTemplateByIdAndUserIdCommand: GetTemplateByIdAndUserIdCommand,
    ): TemplateWithCheckedCount = getTemplateByIdWithPercentOrFail(getTemplateByIdAndUserIdCommand)

    fun getTemplateByIdWithPercentOrFail(
        getTemplateByIdAndUserIdCommand: GetTemplateByIdAndUserIdCommand,
    ): TemplateWithCheckedCount {
        val template =
            templateRepository.getByIdWithPercent(
                getTemplateByIdAndUserIdCommand.id,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        if (template.userId.userId == getTemplateByIdAndUserIdCommand.userId) {
            return template
        }
        throw CustomException.responseBody(ExceptionCode.E_403_000)
    }

    fun getTemplateByIdAndUserIdOrFail(getTemplateByIdAndUserIdCommand: GetTemplateByIdAndUserIdCommand): Template =
        templateRepository.getByIdAndUserId(
            getTemplateByIdAndUserIdCommand.id,
            getTemplateByIdAndUserIdCommand.userId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

    @Transactional
    fun updateSharedById(updateTemplateSharedByIdCommand: UpdateTemplateSharedByIdCommand): Template {
        if (!this.checkedOwnerByUserIdAndId(
                updateTemplateSharedByIdCommand.userId,
                updateTemplateSharedByIdCommand.id,
            )
        ) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }

        return templateRepository.updateSharedById(
            updateTemplateSharedByIdCommand.id,
            updateTemplateSharedByIdCommand.isShared,
        )
    }

    @Transactional()
    fun updateById(command: UpdateTemplateByIdCommand): Template {
        if (!this.checkedOwnerByUserIdAndId(
                command.userId,
                command.templateId,
            )
        ) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }

        return templateRepository.update(
            command.templateId,
            command.name,
            command.thumbnailIndex,
        )
    }

    @Transactional
    fun copyOwnTemplateInComplete(copyTemplateInCompleteCommand: CopyTemplateInCompleteCommand): Template {
        val template =
            this.getTemplateByIdAndUserIdOrFail(
                GetTemplateByIdAndUserIdCommand(
                    copyTemplateInCompleteCommand.id,
                    copyTemplateInCompleteCommand.userId,
                ),
            )

        val newTemplate = this.create(name = template.name.name, userId = template.userId.userId)

        val baskets = basketRepository.getByTemplateId(copyTemplateInCompleteCommand.id)
        if (baskets.isEmpty()) return newTemplate

        val (checkedItems, nonCheckedItems) = baskets.partition { it.checked.checked }

        if (nonCheckedItems.isEmpty()) return newTemplate

        this.createNewBasketsByTemplateId(nonCheckedItems, newTemplate.id.id)

        return newTemplate
    }

    @Transactional
    fun copyOwnTemplate(copyOwnTemplateCommand: CopyOwnTemplateCommand): Template {
        val template =
            this.getTemplateByIdAndUserIdOrFail(
                GetTemplateByIdAndUserIdCommand(
                    copyOwnTemplateCommand.id,
                    copyOwnTemplateCommand.userId,
                ),
            )

        val newTemplate = this.create(name = template.name.name, userId = template.userId.userId)

        val baskets = basketRepository.getByTemplateId(copyOwnTemplateCommand.id)
        if (baskets.isEmpty()) return newTemplate

        this.createNewBasketsByTemplateId(baskets, newTemplate.id.id)
        return newTemplate
    }

    @Transactional
    fun copyTemplate(copyTemplateCommand: CopyTemplateCommand): Template {
        val template =
            templateRepository.getById(
                copyTemplateCommand.id,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_404_001)

        if (!template.isPublicTemplate()) throw CustomException.responseBody(ExceptionCode.E_403_001)

        val newTemplate = this.create(name = template.name.name, userId = template.userId.userId)

        val baskets = basketRepository.getByTemplateId(copyTemplateCommand.id)
        if (baskets.isEmpty()) return newTemplate

        this.createNewBasketsByTemplateId(baskets, newTemplate.id.id)
        return newTemplate
    }

    @Transactional(propagation = Propagation.MANDATORY)
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

    @Transactional(propagation = Propagation.MANDATORY)
    fun create(
        name: String,
        userId: Long,
    ): Template {
        val template =
            templateRepository.create(
                name = name,
                userId = userId,
            )
        ownerPermissionService.createPermission(
            userId,
            template.id.id,
        )
        return template
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
