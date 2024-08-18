package kr.co.shoppingcart.cart.usecase

import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.mock.MockBasketRepository
import kr.co.shoppingcart.cart.mock.MockCategoryRepository
import kr.co.shoppingcart.cart.mock.MockTemplateRepository

class BasketUseCaseTest(
    basketRepository: MockBasketRepository,
    categoryRepository: MockCategoryRepository,
    templateRepository: MockTemplateRepository,
) : BasketUseCase(basketRepository, categoryRepository, templateRepository)
