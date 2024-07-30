package kr.co.shoppingcart.cart.domain.basket

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketsByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.CategoryRepository
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import org.springframework.stereotype.Service

@Service
class BasketUseCase(
    private val basketRepository: BasketRepository,
    private val categoryRepository: CategoryRepository,
    private val templateRepository: TemplateRepository,
) {
    fun create(createBasketCommand: CreateBasketCommand) {
        val category =
            categoryRepository.getById(createBasketCommand.categoryId)
                ?: throw CustomException.responseBody(ExceptionCode.E_400_000)
        val template =
            templateRepository.getByIdAndUserId(createBasketCommand.templatedId, createBasketCommand.userId)
                ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

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
        if (!validatedByUserIdAndBasketId(
                updateBasketFlagCommand.userId,
                updateBasketFlagCommand.basketId,
            )
        ) {
            throw CustomException.responseBody(ExceptionCode.E_403_000)
        }
        this.basketRepository.updateCheckedById(
            updateBasketFlagCommand.basketId,
            updateBasketFlagCommand.checked,
        )
    }

    fun getOwnByTemplateId(command: GetBasketsByTemplateIdCommand): List<Basket> {
        templateRepository.getByIdAndUserId(command.templateId, command.userId)
            ?: throw CustomException.responseBody(ExceptionCode.E_403_000)

        return this.getByTemplateId(command.templateId, command.page, command.size)
    }

    private fun validatedByUserIdAndBasketId(
        userId: Long,
        basketId: Long,
    ): Boolean {
        val basket = basketRepository.getById(basketId) ?: throw CustomException.responseBody(ExceptionCode.E_400_000)
        return basket.template.userId.userId == userId
    }

    private fun getByTemplateId(
        templateId: Long,
        page: Long,
        size: Long,
    ): List<Basket> = basketRepository.getByTemplateIdWithPageNation(templateId, page, size)
}
