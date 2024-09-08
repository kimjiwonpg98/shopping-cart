package kr.co.shoppingcart.cart.usecase

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import kr.co.shoppingcart.cart.mock.MockBasketRepository
import kr.co.shoppingcart.cart.mock.MockCategoryRepository
import kr.co.shoppingcart.cart.mock.MockPermissionsRepository
import kr.co.shoppingcart.cart.mock.MockTemplateRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BasketUseCaseTest {
    private val basketRepository = MockBasketRepository()
    private val categoryRepository = MockCategoryRepository()
    private val templateRepository = MockTemplateRepository()
    private val permissionsRepository = MockPermissionsRepository()

    private val basketUseCase =
        BasketUseCase(basketRepository, categoryRepository, templateRepository, permissionsRepository)

    @Nested
    @DisplayName("생성 테스트")
    inner class Save {
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

        @Test
        fun `생성 시 템플릿이 없으면 에러를 뱉는다`() {
            val exception =
                assertThrows<CustomException> {
                    basketUseCase.create(
                        CreateBasketCommand(
                            templatedId = 100L,
                            name = "test",
                            categoryId = 1L,
                            userId = 1,
                        ),
                    )
                }

            assertEquals(ExceptionCode.E_403_000.name, exception.code.name)
        }

        @Test
        fun `정상적으로 저장될 시 반환값은 없다`() {
            val result =
                basketUseCase.create(
                    CreateBasketCommand(
                        templatedId = 1L,
                        name = "test",
                        categoryId = 1L,
                        userId = 1,
                    ),
                )

            assertEquals(result, Unit)
        }
    }

    @Nested
    @DisplayName("조회 테스트")
    inner class Get {
        @Test
        fun `물품이 없을 시 에러 발생`() {
            val exception =
                assertThrows<CustomException> {
                    basketUseCase.updateIsAddedByFlagAndId(
                        UpdateBasketFlagCommand(
                            userId = 1L,
                            basketId = 100L,
                            checked = true,
                        ),
                    )
                }

            assertEquals(ExceptionCode.E_404_002.name, exception.code.name)
        }

        @Test
        fun `자신의 물품이 아닐 시 에러 발생`() {
            val exception =
                assertThrows<CustomException> {
                    basketUseCase.updateIsAddedByFlagAndId(
                        UpdateBasketFlagCommand(
                            userId = 2L,
                            basketId = 1L,
                            checked = true,
                        ),
                    )
                }

            assertEquals(ExceptionCode.E_403_000.name, exception.code.name)
        }

        @Test
        fun `정상적으로 저장될 시 반환값은 없다`() {
            val result =
                basketUseCase.updateIsAddedByFlagAndId(
                    UpdateBasketFlagCommand(
                        userId = 1L,
                        basketId = 1L,
                        checked = true,
                    ),
                )

            assertEquals(result, Unit)
        }
    }
}
