package kr.co.shoppingcart.cart.domain.basket

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketsByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.CategoryRepository
import kr.co.shoppingcart.cart.domain.permissions.PermissionsRepository
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class BasketUseCase(
    private val basketRepository: BasketRepository,
    private val categoryRepository: CategoryRepository,
    private val templateRepository: TemplateRepository,
    private val permissionsRepository: PermissionsRepository,
) {
    fun create(createBasketCommand: CreateBasketCommand) {
        val category =
            categoryRepository.getById(createBasketCommand.categoryId)
                ?: throw CustomException.responseBody(ExceptionCode.E_400_000)
        val template =
            templateRepository.getById(createBasketCommand.templatedId)
                ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        val permission =
            permissionsRepository.getByUserIdAndTemplateId(
                createBasketCommand.userId,
                createBasketCommand.templatedId,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        if (!permission.checkWritePermissionByLevel()) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }

        basketRepository.save(
            Basket.toDomain(
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
            template,
            category,
        )
    }

    fun updateIsAddedByFlagAndId(updateBasketFlagCommand: UpdateBasketFlagCommand) {
        val basket =
            basketRepository.getById(updateBasketFlagCommand.basketId)
                ?: throw CustomException.responseBody(ExceptionCode.E_404_002)

        if (!basket.validatedByUserId(updateBasketFlagCommand.userId)) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }

        val permission =
            permissionsRepository.getByUserIdAndTemplateId(
                updateBasketFlagCommand.userId,
                basket.templateId!!.templateId,
            ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        if (!permission.checkWritePermissionByLevel()) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }

        this.basketRepository.updateCheckedById(
            updateBasketFlagCommand.basketId,
            updateBasketFlagCommand.checked,
        )
    }

    fun getOwnByTemplateId(command: GetBasketsByTemplateIdCommand): List<Basket> {
        val template =
            templateRepository.getById(command.templateId)
                ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        permissionsRepository.getByUserIdAndTemplateId(
            command.userId,
            template.id.id,
        ) ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return this.getByTemplateId(command.templateId, command.page, command.size)
    }

    @Cacheable(
        value = ["template"],
        key = "#templateId",
    )
    fun getByTemplateIdAndSizeOrderByUpdatedDesc(
        templateId: Long,
        size: Int,
    ): List<Basket> = basketRepository.getByTemplateIdAndSizeOrderByUpdatedDesc(templateId, size)

    private fun getByTemplateId(
        templateId: Long,
        page: Long,
        size: Long,
    ): List<Basket> = basketRepository.getByTemplateIdWithPageNation(templateId, page, size)
}
