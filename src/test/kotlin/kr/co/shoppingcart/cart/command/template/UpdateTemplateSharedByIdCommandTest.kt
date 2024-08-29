package kr.co.shoppingcart.cart.command.template

import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateSharedByIdCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class UpdateTemplateSharedByIdCommandTest {
    private val validatorFactory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
    private val validator = validatorFactory.validator

    @Test
    fun `성공 케이스`() {
        val command =
            UpdateTemplateSharedByIdCommand(
                id = 1L,
                userId = 1L,
                isShared = true,
            )

        val violations = validator.validate(command)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `id는 1이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                UpdateTemplateSharedByIdCommand(
                    id = 0L,
                    userId = 1L,
                    isShared = true,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("id"))
    }

    @Test
    fun `userId는 1이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                UpdateTemplateSharedByIdCommand(
                    id = 1L,
                    userId = 0L,
                    isShared = true,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("user"))
    }
}
