package kr.co.shoppingcart.cart.command.permissions

import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.permissions.command.GetPermissionByUserIdAndTemplateIdCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetPermissionByUserIdAndTemplateIdCommandTest {
    private val validatorFactory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
    private val validator = validatorFactory.validator

    @Test
    fun `성공 케이스`() {
        val command =
            GetPermissionByUserIdAndTemplateIdCommand(
                userId = 1L,
                templateId = 1L,
            )

        val violations = validator.validate(command)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `templateId는 1이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                GetPermissionByUserIdAndTemplateIdCommand(
                    userId = 1L,
                    templateId = 1L,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("template"))
    }

    @Test
    fun `userId는 1이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                GetPermissionByUserIdAndTemplateIdCommand(
                    userId = 1L,
                    templateId = 1L,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("user"))
    }
}
