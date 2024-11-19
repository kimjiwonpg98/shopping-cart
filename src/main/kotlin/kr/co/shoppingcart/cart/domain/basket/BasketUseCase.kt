package kr.co.shoppingcart.cart.domain.basket

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.command.DeleteBasketByIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketByIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketByTemplateIdAndCategoryIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketsByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketContentCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import kr.co.shoppingcart.cart.domain.basket.service.BasketCreationService
import kr.co.shoppingcart.cart.domain.basket.service.BasketUpdateService
import kr.co.shoppingcart.cart.domain.basket.service.GetBasketService
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.services.GetCategoryService
import kr.co.shoppingcart.cart.domain.permissions.services.OwnerPermissionService
import kr.co.shoppingcart.cart.domain.permissions.services.ReaderPermissionService
import kr.co.shoppingcart.cart.domain.permissions.services.WriterPermissionService
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
    private val ownerPermissionService: OwnerPermissionService,
    private val writerPermissionService: WriterPermissionService,
    private val readerPermissionService: ReaderPermissionService,
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
    fun getOwnByTemplateId(command: GetBasketsByTemplateIdCommand): List<Basket> {
        ownerPermissionService.getByUserIdAndTemplateId(
            command.userId,
            command.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return getBasketService.getByTemplateId(command.templateId)
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

    fun getByTemplateIdAndCategoryId(command: GetBasketByTemplateIdAndCategoryIdCommand): List<Basket> {
        readerPermissionService.getOverLevelByUserIdAndTemplateId(
            command.userId,
            command.templateId,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return getBasketService.getByTemplateIdAndCategoryIdByUpdatedDesc(command.templateId, command.categoryId)
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
}
