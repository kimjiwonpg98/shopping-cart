package kr.co.shoppingcart.cart.domain.template

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.service.GetBasketService
import kr.co.shoppingcart.cart.domain.permissions.services.PermissionService
import kr.co.shoppingcart.cart.domain.template.command.CopyOwnTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateInCompleteCommand
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.DeleteByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetTemplateByIdAndUserIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetWithCompletePercentAndPreviewCommand
import kr.co.shoppingcart.cart.domain.template.command.PinnedTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.UnpinnedTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateByIdCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateSharedByIdCommand
import kr.co.shoppingcart.cart.domain.template.services.CreateTemplateService
import kr.co.shoppingcart.cart.domain.template.services.DeleteTemplateService
import kr.co.shoppingcart.cart.domain.template.services.GetTemplateService
import kr.co.shoppingcart.cart.domain.template.services.UpdateTemplateService
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TemplateUseCase(
    private val getBasketService: GetBasketService,
    private val createTemplateService: CreateTemplateService,
    private val getTemplateService: GetTemplateService,
    private val deleteTemplateService: DeleteTemplateService,
    private val updateTemplateService: UpdateTemplateService,
    @Qualifier("ownerPermissionService")
    private val ownerPermissionService: PermissionService,
    @Qualifier("readerPermissionService")
    private val readerPermissionService: PermissionService,
) {
    @Transactional
    fun createByApi(createTemplateCommand: CreateTemplateCommand): Template =
        this.createAsOwner(createTemplateCommand.name, createTemplateCommand.userId)

    @Transactional(readOnly = true)
    fun getByIdAndUserIdToRead(
        getTemplateByIdAndUserIdCommand: GetTemplateByIdAndUserIdCommand,
    ): TemplateWithCheckedCount {
        readerPermissionService.getOverLevelByUserIdAndTemplateId(
            getTemplateByIdAndUserIdCommand.userId,
            getTemplateByIdAndUserIdCommand.id,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return getTemplateService.getTemplateByIdWithPercentOrFail(getTemplateByIdAndUserIdCommand)
    }

    @Transactional
    fun updateSharedById(updateTemplateSharedByIdCommand: UpdateTemplateSharedByIdCommand): Template {
        ownerPermissionService.getByUserIdAndTemplateId(
            updateTemplateSharedByIdCommand.userId,
            updateTemplateSharedByIdCommand.id,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return updateTemplateService.updateSharedById(
            updateTemplateSharedByIdCommand.id,
            updateTemplateSharedByIdCommand.isShared,
        )
    }

    @Transactional
    fun updateById(updateTemplateByIdCommand: UpdateTemplateByIdCommand): Template {
        ownerPermissionService.getByUserIdAndTemplateId(
            updateTemplateByIdCommand.userId,
            updateTemplateByIdCommand.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return updateTemplateService.update(
            updateTemplateByIdCommand.templateId,
            updateTemplateByIdCommand.name,
            updateTemplateByIdCommand.thumbnailIndex,
        )
    }

    @Transactional
    fun copyOwnTemplateInComplete(copyTemplateInCompleteCommand: CopyTemplateInCompleteCommand): Template {
        ownerPermissionService.getByUserIdAndTemplateId(
            copyTemplateInCompleteCommand.userId,
            copyTemplateInCompleteCommand.id,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        val template =
            this.getTemplateService.getByIdOrFail(
                copyTemplateInCompleteCommand.id,
            )

        val newTemplate = this.createAsOwner(name = template.name.name, userId = template.userId.userId)

        val baskets = getBasketService.getByTemplateId(copyTemplateInCompleteCommand.id)
        if (baskets.isEmpty()) return newTemplate

        val (_, nonCheckedItems) = baskets.partition { it.checked.checked }

        if (nonCheckedItems.isEmpty()) return newTemplate

        this.createTemplateService.createNewBasketsByTemplateId(nonCheckedItems, newTemplate.id.id)

        return newTemplate
    }

    @Transactional
    fun copyOwnTemplate(copyOwnTemplateCommand: CopyOwnTemplateCommand): Template {
        ownerPermissionService.getByUserIdAndTemplateId(
            copyOwnTemplateCommand.userId,
            copyOwnTemplateCommand.id,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        val template =
            this.getTemplateService.getByIdOrFail(
                copyOwnTemplateCommand.id,
            )

        val newTemplate = this.createAsOwner(name = template.name.name, userId = template.userId.userId)

        val baskets = getBasketService.getByTemplateId(copyOwnTemplateCommand.id)
        if (baskets.isEmpty()) return newTemplate

        this.createTemplateService.createNewBasketsByTemplateId(baskets, newTemplate.id.id)
        return newTemplate
    }

    @Transactional
    fun copyTemplate(copyTemplateCommand: CopyTemplateCommand): Template {
        val template =
            this.getTemplateService.getByIdOrFail(
                copyTemplateCommand.id,
            )

        if (!template.isPublicTemplate()) throw CustomException.responseBody(ExceptionCode.E_403_001)

        val newTemplate =
            this.createAsOwner(
                name = template.name.name,
                userId = copyTemplateCommand.userId,
                thumbnailIndex = template.thumbnailIndex.thumbnail,
            )

        val baskets = getBasketService.getByTemplateId(copyTemplateCommand.id)
        if (baskets.isEmpty()) return newTemplate

        createTemplateService.createNewBasketsByTemplateId(baskets, newTemplate.id.id)
        return newTemplate
    }

    fun getWithCompletePercentAndPreview(
        getWithCompletePercentAndPreviewCommand: GetWithCompletePercentAndPreviewCommand,
    ): List<TemplateWithCheckedCount> =
        getTemplateService.getWithCompletePercentAndPreview(
            getWithCompletePercentAndPreviewCommand.userId,
        )

    fun deleteByIdAndUserId(deleteByTemplateIdCommand: DeleteByTemplateIdCommand) {
        ownerPermissionService.getByUserIdAndTemplateId(
            deleteByTemplateIdCommand.userId,
            deleteByTemplateIdCommand.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        deleteTemplateService.deleteById(deleteByTemplateIdCommand.templateId)
    }

    @Transactional
    fun createAsOwner(
        name: String,
        userId: Long,
        thumbnailIndex: Int = 1,
    ): Template {
        val template =
            this.createTemplateService.create(
                name = name,
                thumbnailIndex = thumbnailIndex,
                userId = userId,
            )
        ownerPermissionService.createPermission(
            userId = userId,
            templateId = template.id.id,
        )
        return template
    }

    @Transactional(readOnly = true)
    fun getDefaultNameByUserId(userId: Long): Long {
        val regexNumber = """ ([0-9]+$)""".toRegex()

        return getTemplateService.getByUserIdForDefaultName(userId)?.let {
            regexNumber
                .find(it.name.name)
                ?.value
                ?.replace(" ", "")
                ?.toLongOrNull()
                ?.plus(1) ?: 1
        } ?: 1
    }

    fun getCountByUserId(userId: Long): Long = getTemplateService.getCountByUserId(userId)

    fun pinnedTemplate(pinnedTemplateCommand: PinnedTemplateCommand): Template {
        ownerPermissionService.getByUserIdAndTemplateId(
            pinnedTemplateCommand.userId,
            pinnedTemplateCommand.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return updateTemplateService.pinnedTemplate(
            pinnedTemplateCommand.templateId,
        )
    }

    fun unpinnedTemplate(unpinnedTemplateCommand: UnpinnedTemplateCommand): Template {
        ownerPermissionService.getByUserIdAndTemplateId(
            unpinnedTemplateCommand.userId,
            unpinnedTemplateCommand.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return updateTemplateService.unpinnedTemplate(
            unpinnedTemplateCommand.templateId,
        )
    }
}
