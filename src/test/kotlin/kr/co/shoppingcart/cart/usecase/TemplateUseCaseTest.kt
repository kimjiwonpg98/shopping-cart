package kr.co.shoppingcart.cart.usecase

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.permissions.PermissionsRepository
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import kr.co.shoppingcart.cart.domain.template.TemplateUseCase
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateInCompleteCommand
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.GetTemplateByIdAndUserIdCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateSharedByIdCommand
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.mock.vo.MockTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(
    MockitoExtension::class,
)
class TemplateUseCaseTest {
    @Mock
    private lateinit var templateRepository: TemplateRepository

    @Mock
    private lateinit var basketRepository: BasketRepository

    @Mock
    private lateinit var permissionsRepository: PermissionsRepository

    @InjectMocks
    private lateinit var templateUseCase: TemplateUseCase

    @BeforeEach
    fun setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this)
    }

    @Nested
    @DisplayName("createByApi test")
    inner class CreateByApiTest {
        var createTemplateCommand =
            CreateTemplateCommand(
                name = "test",
                userId = 1,
            )

        @Test
        fun `정상적으로 생성된다`() {
            `when`(
                templateRepository.create(
                    name = createTemplateCommand.name,
                    userId = createTemplateCommand.userId,
                ),
            ).thenReturn(
                MockTemplate.getTemplateByCreate(createTemplateCommand),
            )

            templateUseCase = TemplateUseCase(templateRepository, basketRepository, permissionsRepository)

            val result =
                templateUseCase.createByApi(createTemplateCommand)

            assertInstanceOf(Template::class.java, result)
        }
    }

    @Nested
    @DisplayName("getTemplateByIdAndUserIdOrFail test")
    inner class GetTemplateByIdAndUserIdOrFailTest {
        var defaultCommand =
            GetTemplateByIdAndUserIdCommand(
                id = 1,
                userId = 1,
            )

        @Test
        fun `없으면 에러를 뱉는다`() {
            `when`(
                templateRepository.getByIdAndUserId(
                    id = defaultCommand.id,
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                null,
            )

            templateUseCase = TemplateUseCase(templateRepository, basketRepository, permissionsRepository)

            val exception =
                org.junit.jupiter.api.assertThrows<CustomException> {
                    templateUseCase.getTemplateByIdAndUserIdOrFail(defaultCommand)
                }

            assertEquals(ExceptionCode.E_403_000.name, exception.code.name)
        }

        @Test
        fun `있으면 조회한다`() {
            `when`(
                templateRepository.getByIdAndUserId(
                    id = defaultCommand.id,
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(defaultCommand.id),
            )

            templateUseCase = TemplateUseCase(templateRepository, basketRepository, permissionsRepository)

            val result =
                templateUseCase.getTemplateByIdAndUserIdOrFail(defaultCommand)

            assertInstanceOf(Template::class.java, result)
        }
    }

    @Nested
    @DisplayName("updateSharedById test")
    inner class UpdateSharedByIdTest {
        var defaultCommand =
            UpdateTemplateSharedByIdCommand(
                id = 1,
                userId = 1,
                isShared = true,
            )

        @Test
        fun `본인의 tempalte이 아니라면 에러`() {
            `when`(
                templateRepository.getByIdAndUserId(
                    id = defaultCommand.id,
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                null,
            )

            templateUseCase = TemplateUseCase(templateRepository, basketRepository, permissionsRepository)

            val exception =
                org.junit.jupiter.api.assertThrows<CustomException> {
                    templateUseCase.updateSharedById(defaultCommand)
                }

            assertEquals(ExceptionCode.E_403_000.name, exception.code.name)
        }

        @Test
        fun `반환값은 Unit`() {
            `when`(
                templateRepository.getByIdAndUserId(
                    id = defaultCommand.id,
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(defaultCommand.id),
            )

            templateUseCase = TemplateUseCase(templateRepository, basketRepository, permissionsRepository)

            val result =
                templateUseCase.updateSharedById(defaultCommand)

            assertEquals(Unit, result)
        }
    }

    @Nested
    @DisplayName("copyOwnTemplateInComplete test")
    inner class CopyOwnTemplateInCompleteTest {
        var defaultCommand =
            CopyTemplateInCompleteCommand(
                id = 1,
                userId = 1,
            )

        @Test
        fun `본인의 tempalte이 아니라면 에러`() {
            `when`(
                templateRepository.getByIdAndUserId(
                    id = defaultCommand.id,
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                null,
            )

            templateUseCase = TemplateUseCase(templateRepository, basketRepository, permissionsRepository)

            val exception =
                org.junit.jupiter.api.assertThrows<CustomException> {
                    templateUseCase.copyOwnTemplateInComplete(defaultCommand)
                }

            assertEquals(ExceptionCode.E_403_000.name, exception.code.name)
        }

        @Test
        fun `basket이 없으면 처리 x`() {
            `when`(
                templateRepository.getByIdAndUserId(
                    id = defaultCommand.id,
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(defaultCommand.id),
            )

            `when`(
                templateRepository.create(
                    name = "test",
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(2),
            )

            `when`(
                basketRepository.getByTemplateId(
                    defaultCommand.id,
                ),
            ).thenReturn(
                emptyList(),
            )

            templateUseCase = TemplateUseCase(templateRepository, basketRepository, permissionsRepository)

            templateUseCase.copyOwnTemplateInComplete(defaultCommand)

            verify(basketRepository, times(0)).bulkSave(anyList())
        }
    }
}
