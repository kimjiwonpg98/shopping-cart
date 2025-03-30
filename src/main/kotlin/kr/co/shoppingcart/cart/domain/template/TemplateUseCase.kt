package kr.co.shoppingcart.cart.domain.template

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.core.permission.application.port.input.CreatePermission
import kr.co.shoppingcart.cart.core.permission.application.port.input.CreatePermissionCommand
import kr.co.shoppingcart.cart.core.permission.application.port.input.ValidPermission
import kr.co.shoppingcart.cart.core.permission.application.port.input.ValidPermissionCommand
import kr.co.shoppingcart.cart.core.permission.application.port.input.ValidPermissionIsOverLevelCommand
import kr.co.shoppingcart.cart.core.permission.domain.PermissionLevel
import kr.co.shoppingcart.cart.domain.basket.service.GetBasketService
import kr.co.shoppingcart.cart.domain.template.command.CopyOwnTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateInCompleteCommand
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.DeleteByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetTemplateByIdAndUserIdCommand
import kr.co.shoppingcart.cart.domain.template.command.GetWithCompletePercentAndPreviewCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateByIdCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateSharedByIdCommand
import kr.co.shoppingcart.cart.domain.template.services.CreateTemplateService
import kr.co.shoppingcart.cart.domain.template.services.DeleteTemplateService
import kr.co.shoppingcart.cart.domain.template.services.GetTemplateService
import kr.co.shoppingcart.cart.domain.template.services.UpdateTemplateService
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TemplateUseCase(
    private val getBasketService: GetBasketService,
    private val createTemplateService: CreateTemplateService,
    private val getTemplateService: GetTemplateService,
    private val deleteTemplateService: DeleteTemplateService,
    private val updateTemplateService: UpdateTemplateService,
    private val validPermission: ValidPermission,
    private val createPermission: CreatePermission,
) {
    @Transactional
    fun createByApi(createTemplateCommand: CreateTemplateCommand): Template =
        this.createAsOwner(createTemplateCommand.name, createTemplateCommand.userId)

    @Transactional(readOnly = true)
    fun getByIdAndUserIdToRead(command: GetTemplateByIdAndUserIdCommand): TemplateWithCheckedCount {
        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = command.id,
                level = PermissionLevel.READER_LEVEL,
            ),
        )

        return getTemplateService.getTemplateByIdWithPercentOrFail(command)
    }

    @Transactional
    fun updateSharedById(command: UpdateTemplateSharedByIdCommand): Template {
        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = command.id,
                level = PermissionLevel.OWNER_LEVEL,
            ),
        )

        return updateTemplateService.updateSharedById(
            command.id,
            command.isShared,
        )
    }

    @Transactional
    fun updateById(command: UpdateTemplateByIdCommand): Template {
        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = command.templateId,
                level = PermissionLevel.OWNER_LEVEL,
            ),
        )

        return updateTemplateService.update(
            command.templateId,
            command.name,
            command.thumbnailIndex,
        )
    }

    @Transactional
    fun copyOwnTemplateInComplete(command: CopyTemplateInCompleteCommand): Template {
        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = command.id,
                level = PermissionLevel.OWNER_LEVEL,
            ),
        )

        val template =
            this.getTemplateService.getByIdOrFail(
                command.id,
            )

        val newTemplate = this.createAsOwner(name = template.name.name, userId = template.userId.userId)

        val baskets = getBasketService.getByTemplateId(command.id)
        if (baskets.isEmpty()) return newTemplate

        val (_, nonCheckedItems) = baskets.partition { it.checked.checked }

        if (nonCheckedItems.isEmpty()) return newTemplate

        this.createTemplateService.createNewBasketsByTemplateId(nonCheckedItems, newTemplate.id.id)

        return newTemplate
    }

    @Transactional
    fun copyOwnTemplate(command: CopyOwnTemplateCommand): Template {
        validPermission.validate(
            ValidPermissionCommand(
                userId = command.userId,
                templateId = command.id,
                level = PermissionLevel.OWNER_LEVEL,
            ),
        )

        val template =
            this.getTemplateService.getByIdOrFail(
                command.id,
            )

        val newTemplate = this.createAsOwner(name = template.name.name, userId = template.userId.userId)

        val baskets = getBasketService.getByTemplateId(command.id)
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

    fun deleteByIdAndUserId(command: DeleteByTemplateIdCommand) {
        validPermission.validate(
            ValidPermissionCommand(
                userId = command.userId,
                templateId = command.templateId,
                level = PermissionLevel.OWNER_LEVEL,
            ),
        )

        deleteTemplateService.deleteById(command.templateId)
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
        createPermission.create(
            CreatePermissionCommand(
                userId = userId,
                templateId = template.id.id,
                level = PermissionLevel.OWNER_LEVEL,
            ),
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
}
