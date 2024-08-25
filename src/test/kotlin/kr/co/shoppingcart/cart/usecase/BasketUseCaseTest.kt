package kr.co.shoppingcart.cart.usecase

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.mock.MockBasketRepository
import kr.co.shoppingcart.cart.mock.MockCategoryRepository
import kr.co.shoppingcart.cart.mock.MockTemplateRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BasketUseCaseTest {
    private val basketRepository = MockBasketRepository()
    private val categoryRepository = MockCategoryRepository()
    private val templateRepository = MockTemplateRepository()

    private val basketUseCase = BasketUseCase(basketRepository, categoryRepository, templateRepository)

    @Test
    fun `생성 시 카테고리가 없으면 에러를 뱉는다`() {
        val exception =
            assertThrows<CustomException> {
                basketUseCase.create(
                    CreateBasketCommand(
                        templatedId = 1L,
                        name = "test",
                        categoryId = 100L,
                        userId = 1,
                    ),
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
    }
}
