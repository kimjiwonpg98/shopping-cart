package kr.co.shoppingcart.cart.command.basket

import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateBasketCommandTest {
    private val validatorFactory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
    private val validator = validatorFactory.validator

    @Test
    fun `성공 케이스`() {
        val command =
            CreateBasketCommand(
                templateId = 1L,
                name = "Test",
                categoryName = "기타",
                userId = 3L,
                checked = true,
                count = 5L,
            )

        val violations = validator.validate(command)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `templateId는 1이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                CreateBasketCommand(
                    templateId = 0L,
                    name = "Test",
                    categoryName = "기타",
                    userId = 3L,
                    checked = true,
                    count = 5L,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("template"))
    }

    @Test
    fun `name은 빈값이 들어올 수 없다`() {
        val exception =
            assertThrows<CustomException> {
                CreateBasketCommand(
                    templateId = 1L,
                    name = " ",
                    categoryName = "기타",
                    userId = 3L,
                    checked = true,
                    count = 5L,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("name"))
    }

    @Test
    fun `카테고리는 값이 있어야한다`() {
        val exception =
            assertThrows<CustomException> {
                CreateBasketCommand(
                    templateId = 1L,
                    name = "test",
                    categoryName = "",
                    userId = 3L,
                    checked = true,
                    count = 5L,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("category"))
    }

    @Test
    fun `기본값이 있는 것들은 기본값이 적용된다`() {
        val command =
            CreateBasketCommand(
                templateId = 1L,
                name = "Basket Name",
                categoryName = "기타",
                userId = 1L,
            )

        assertEquals(1, command.count)
        assertEquals(false, command.checked)
    }
}
