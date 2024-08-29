package kr.co.shoppingcart.cart.command.template

import jakarta.validation.Validation
import jakarta.validation.ValidatorFactory
import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.template.command.GetWithCompletePercentAndPreviewCommand
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetWithCompletePercentAndPreviewCommandTest {
    private val validatorFactory: ValidatorFactory = Validation.buildDefaultValidatorFactory()
    private val validator = validatorFactory.validator

    @Test
    fun `성공 케이스`() {
        val command =
            GetWithCompletePercentAndPreviewCommand(
                userId = 1L,
                page = 1,
                size = 1,
            )

        val violations = validator.validate(command)

        assertTrue(violations.isEmpty())
    }

    @Test
    fun `userId는 1이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                GetWithCompletePercentAndPreviewCommand(
                    userId = 0L,
                    page = 1,
                    size = 1,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("user"))
    }

    @Test
    fun `page는 0이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                GetWithCompletePercentAndPreviewCommand(
                    userId = 1L,
                    page = -1,
                    size = 1,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("page"))
    }

    @Test
    fun `size는 1이상이어야한다`() {
        val exception =
            assertThrows<CustomException> {
                GetWithCompletePercentAndPreviewCommand(
                    userId = 1L,
                    page = 0,
                    size = 0,
                )
            }

        assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        assertTrue(exception.detailInformation!!.contains("size"))
    }
}
