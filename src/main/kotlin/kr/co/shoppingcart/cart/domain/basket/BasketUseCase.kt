package kr.co.shoppingcart.cart.domain.basket

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
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
import kr.co.shoppingcart.cart.domain.permissions.services.PermissionService
import kr.co.shoppingcart.cart.domain.template.services.GetTemplateService
import org.springframework.beans.factory.annotation.Qualifier
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
    @Qualifier("writerPermissionService")
    private val writerPermissionService: PermissionService,
    @Qualifier("readerPermissionService")
    private val readerPermissionService: PermissionService,
) {
    @Transactional
    fun create(createBasketCommand: CreateBasketCommand): Basket {
        val category =
            getCategoryService.getByNameOrBasic(createBasketCommand.categoryName)

        val template =
            getTemplateService.getByIdOrFail(createBasketCommand.templateId)

        writerPermissionService.getOverLevelByUserIdAndTemplateId(
            createBasketCommand.userId,
            createBasketCommand.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

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

        writerPermissionService.getOverLevelByUserIdAndTemplateId(
            updateBasketFlagCommand.userId,
            basket.templateId!!.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return this.basketUpdateService.updateCheckedById(
            updateBasketFlagCommand.basketId,
            updateBasketFlagCommand.checked,
        )
    }

    @Transactional
    fun updateAllChecked(command: UpdateAllCheckedCommand) {
        val baskets = getBasketService.getByTemplateId(command.templateId)

        writerPermissionService.getOverLevelByUserIdAndTemplateId(
            command.userId,
            command.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return this.basketUpdateService.updateCheckedAll(
            baskets.filter { !it.checked.checked }.map { it.id.id },
            true,
        )
    }

    @Transactional
    fun getByTemplateId(command: GetBasketsByTemplateIdCommand): List<Basket> {
        readerPermissionService.getOverLevelByUserIdAndTemplateId(
            command.userId,
            command.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

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

        writerPermissionService.getOverLevelByUserIdAndTemplateId(
            command.userId,
            basket.templateId!!.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

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

        writerPermissionService.getOverLevelByUserIdAndTemplateId(
            command.userId,
            basket.templateId!!.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

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

        writerPermissionService.getOverLevelByUserIdAndTemplateId(
            command.userId,
            templateIds[0],
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        this.basketUpdateService.deleteByIds(
            command.basketIds,
        )
    }

    fun getByTemplateIdAndCategoryId(command: GetBasketByTemplateIdAndCategoryIdCommand): List<Basket> {
        readerPermissionService.getOverLevelByUserIdAndTemplateId(
            command.userId,
            command.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return getBasketService.getByTemplateIdAndCategoryIdByUpdatedDesc(command.templateId, command.categoryId)
    }

    fun getByTemplateIdAndCategoryName(command: GetBasketByTemplateIdAndCategoryNameCommand): List<Basket> {
        readerPermissionService.getOverLevelByUserIdAndTemplateId(
            command.userId,
            command.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

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

        readerPermissionService.getOverLevelByUserIdAndTemplateId(
            command.userId,
            basket.templateId!!.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

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
            writerPermissionService.getOverLevelByUserIdAndTemplateId(
                command.userId,
                it.templateId!!.templateId,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)
        }

        return this.basketUpdateService.updateCategoryName(
            command.basketIds,
            command.categoryName,
        )
    }
}
