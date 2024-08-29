package kr.co.shoppingcart.cart.command.template

import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CreateTemplateCommandTest {
    private val validatorFactory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
    private val validator = validatorFactory.validator

    @Test
    fun `성공 케이스`() {
        val command =
            CreateTemplateCommand(
                userId = 1L,
                name = "test",
            )

        val violations = validator.validate(command)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `name의 길이가 1이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                CreateTemplateCommand(
                    userId = 1L,
                    name = "",
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("name"))
    }

    @Test
    fun `name의 길이가 30글자 이하`() {
        val exception =
            assertThrows<CustomException> {
                CreateTemplateCommand(
                    userId = 1L,
                    name = "하".repeat(31),
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("name"))
    }

    @Test
    fun `userId는 1이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                CreateTemplateCommand(
                    userId = 0L,
                    name = "test",
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("user"))
    }
}
