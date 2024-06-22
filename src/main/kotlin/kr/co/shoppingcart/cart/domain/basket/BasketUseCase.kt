package kr.co.shoppingcart.cart.domain.basket

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.CategoryRepository
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import org.springframework.stereotype.Service

@Service
class BasketUseCase (
    // TODO: usecase는 구현체고 interface를 만들어서 Repository랑 변경해야함
    private val basketRepository: BasketRepository,
    private val categoryRepository: CategoryRepository,
    private val templateRepository: TemplateRepository
) {
    fun create(createBasketCommand: CreateBasketCommand) {
        val category = categoryRepository.getById(createBasketCommand.categoryId)
            ?: throw CustomException.badRequest(ExceptionCode.E_400_000)
        val template = templateRepository.getByIdAndUserId(createBasketCommand.templatedId, createBasketCommand.userId)
            ?: throw CustomException.badRequest(ExceptionCode.E_400_000)

        basketRepository.save(Basket.toDomain(
            name = createBasketCommand.name,
            isAdded = createBasketCommand.isAdded?:false,
            category = category,
            template = template,
            count = createBasketCommand.count?: 1,
            createTime = null
        ), template,  category)
    }

}