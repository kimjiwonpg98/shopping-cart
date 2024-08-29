package kr.co.shoppingcart.cart.command.basket

import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UpdateBasketFlagCommandTest {
    private val validatorFactory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
    private val validator = validatorFactory.validator

    @Test
    fun `성공 케이스`() {
        val command =
            UpdateBasketFlagCommand(
                userId = 1L,
                checked = true,
                basketId = 1L,
            )

        val violations = validator.validate(command)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `basketId는 1이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                UpdateBasketFlagCommand(
                    userId = 1L,
                    checked = true,
                    basketId = 0L,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("basket"))
    }

    @Test
    fun `userId는 1이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                UpdateBasketFlagCommand(
                    userId = 0L,
                    checked = true,
                    basketId = 1L,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("user"))
    }
}
