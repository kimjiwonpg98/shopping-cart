package kr.co.shoppingcart.cart.domain.basket

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.core.permission.application.port.input.ValidPermission
import kr.co.shoppingcart.cart.core.permission.application.port.input.ValidPermissionIsOverLevelCommand
import kr.co.shoppingcart.cart.core.permission.domain.PermissionLevel
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.command.DeleteBasketByIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.DeleteBasketsByIdsCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketByIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketByTemplateIdAndCategoryIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketByTemplateIdAndCategoryNameCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketsByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetPublicBasketByTemplateIdAndCategoryIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetPublicBasketByTemplateIdAndCategoryNameCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetPublicBasketsByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateAllCheckedCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketContentCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateCategoryNameCommand
import kr.co.shoppingcart.cart.domain.basket.service.BasketCreationService
import kr.co.shoppingcart.cart.domain.basket.service.BasketUpdateService
import kr.co.shoppingcart.cart.domain.basket.service.GetBasketService
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.basket.vo.BasketListAndTemplate
import kr.co.shoppingcart.cart.domain.category.services.GetCategoryService
import kr.co.shoppingcart.cart.domain.template.services.GetTemplateService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BasketUseCase(
    private val getCategoryService: GetCategoryService,
    private val getBasketService: GetBasketService,
    private val basketUpdateService: BasketUpdateService,
    private val basketCreationService: BasketCreationService,
    private val getTemplateService: GetTemplateService,
    private val validPermission: ValidPermission,
) {
    @Transactional
    fun create(createBasketCommand: CreateBasketCommand): Basket {
        val category =
            getCategoryService.getByNameOrBasic(createBasketCommand.categoryName)

        val template =
            getTemplateService.getByIdOrFail(createBasketCommand.templateId)

        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = createBasketCommand.userId,
                templateId = createBasketCommand.templateId,
                level = PermissionLevel.WRITER_LEVEL,
            ),
        )

        return basketCreationService.save(
            Basket.toDomain(
                id = 0,
                name = createBasketCommand.name,
                checked = createBasketCommand.checked ?: false,
                categoryName = createBasketCommand.categoryName,
                category = category,
                template = template,
                count = createBasketCommand.count ?: 1,
                createdTime = null,
                updatedTime = null,
                templateId = null,
                categoryId = null,
            ),
        )
    }

    @Transactional
    fun updateIsAddedByFlagAndId(updateBasketFlagCommand: UpdateBasketFlagCommand): Basket {
        val basket =
            getBasketService.getByIdOrFail(updateBasketFlagCommand.basketId)

        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = updateBasketFlagCommand.userId,
                templateId = basket.templateId!!.templateId,
                level = PermissionLevel.WRITER_LEVEL,
            ),
        )

        return this.basketUpdateService.updateCheckedById(
            updateBasketFlagCommand.basketId,
            updateBasketFlagCommand.checked,
        )
    }

    @Transactional
    fun updateAllChecked(command: UpdateAllCheckedCommand) {
        val baskets = getBasketService.getByTemplateId(command.templateId)

        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = command.templateId,
                level = PermissionLevel.WRITER_LEVEL,
            ),
        )

        return this.basketUpdateService.updateCheckedAll(
            baskets.filter { !it.checked.checked }.map { it.id.id },
            true,
        )
    }

    @Transactional
    fun getByTemplateId(command: GetBasketsByTemplateIdCommand): List<Basket> {
        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = command.templateId,
                level = PermissionLevel.READER_LEVEL,
            ),
        )

        return getBasketService.getByTemplateId(command.templateId)
    }

    @Transactional(readOnly = true)
    fun getPublicBasketByTemplateId(command: GetPublicBasketsByTemplateIdCommand): BasketListAndTemplate {
        val template = getTemplateService.getByIdOrFail(command.templateId)

        if (!template.isPublicTemplate()) {
            throw CustomException.responseBody(ExceptionCode.E_403_001)
        }

        return BasketListAndTemplate(
            basketList = getBasketService.getByTemplateId(command.templateId),
            template = template,
        )
    }

    @Transactional
    fun updateBasketContent(command: UpdateBasketContentCommand): Basket {
        val basket = getBasketService.getByIdOrFail(command.basketId)

        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = basket.templateId!!.templateId,
                level = PermissionLevel.WRITER_LEVEL,
            ),
        )

        return this.basketUpdateService.updateContent(
            command.basketId,
            command.content,
            command.count,
        )
    }

    @Transactional
    fun deleteById(command: DeleteBasketByIdCommand) {
        val basket =
            this.getBasketService.getByIdOrFail(command.basketId)

        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = basket.templateId!!.templateId,
                level = PermissionLevel.WRITER_LEVEL,
            ),
        )

        this.basketUpdateService.deleteById(
            command.basketId,
        )
    }

    @Transactional
    fun deleteByIds(command: DeleteBasketsByIdsCommand) {
        val baskets =
            this.getBasketService.getByIds(command.basketIds)

        val templateIds = baskets.map { it.templateId!!.templateId }.toSet().toList()

        if (templateIds.size > 1) throw CustomException.responseBody(ExceptionCode.E_400_001)

        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = templateIds[0],
                level = PermissionLevel.WRITER_LEVEL,
            ),
        )

        this.basketUpdateService.deleteByIds(
            command.basketIds,
        )
    }

    fun getByTemplateIdAndCategoryId(command: GetBasketByTemplateIdAndCategoryIdCommand): List<Basket> {
        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = command.templateId,
                level = PermissionLevel.READER_LEVEL,
            ),
        )

        return getBasketService.getByTemplateIdAndCategoryIdByUpdatedDesc(command.templateId, command.categoryId)
    }

    fun getByTemplateIdAndCategoryName(command: GetBasketByTemplateIdAndCategoryNameCommand): List<Basket> {
        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = command.templateId,
                level = PermissionLevel.READER_LEVEL,
            ),
        )

        return getBasketService.getByTemplateIdAndCategoryNameByUpdatedDesc(command.templateId, command.categoryName)
    }

    fun getByTemplateIdAndCategoryIdPublic(command: GetPublicBasketByTemplateIdAndCategoryIdCommand): List<Basket> {
        val template = getTemplateService.getByIdOrFail(command.templateId)

        if (!template.isPublicTemplate()) {
            throw CustomException.responseBody(ExceptionCode.E_403_001)
        }

        return getBasketService.getByTemplateIdAndCategoryIdByUpdatedDesc(command.templateId, command.categoryId)
    }

    fun getByTemplateIdAndCategoryNamePublic(command: GetPublicBasketByTemplateIdAndCategoryNameCommand): List<Basket> {
        val template = getTemplateService.getByIdOrFail(command.templateId)

        if (!template.isPublicTemplate()) {
            throw CustomException.responseBody(ExceptionCode.E_403_001)
        }

        return getBasketService.getByTemplateIdAndCategoryNameByUpdatedDesc(command.templateId, command.categoryName)
    }

    @Transactional(readOnly = true)
    fun getById(command: GetBasketByIdCommand): Basket {
        val basket =
            this.getBasketService.getByIdOrFail(command.basketId)

        validPermission.isOverLevel(
            ValidPermissionIsOverLevelCommand(
                userId = command.userId,
                templateId = basket.templateId!!.templateId,
                level = PermissionLevel.READER_LEVEL,
            ),
        )

        return basket
    }

    @Cacheable(
        value = ["template"],
        key = "#templateId",
    )
    fun getByTemplateIdAndSizeOrderByUpdatedDesc(
        templateId: Long,
        size: Int,
    ): List<Basket> = getBasketService.getByTemplateIdAndSizeOrderByUpdatedDesc(templateId, size)

    fun updateCategoryName(command: UpdateCategoryNameCommand) {
        val baskets = getBasketService.getByIds(command.basketIds)

        baskets.distinctBy { it.templateId!!.templateId }.forEach {
            validPermission.isOverLevel(
                ValidPermissionIsOverLevelCommand(
                    userId = command.userId,
                    templateId = it.templateId!!.templateId,
                    level = PermissionLevel.WRITER_LEVEL,
                ),
            )
        }

        return this.basketUpdateService.updateCategoryName(
            command.basketIds,
            command.categoryName,
        )
    }
}
