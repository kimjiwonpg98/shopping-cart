package kr.co.shoppingcart.cart.usecase

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.service.GetBasketService
import kr.co.shoppingcart.cart.domain.permissions.services.OwnerPermissionService
import kr.co.shoppingcart.cart.domain.permissions.services.ReaderPermissionService
import kr.co.shoppingcart.cart.domain.template.TemplateUseCase
import kr.co.shoppingcart.cart.domain.template.command.CopyOwnTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.CopyTemplateInCompleteCommand
import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.command.UpdateTemplateSharedByIdCommand
import kr.co.shoppingcart.cart.domain.template.services.CreateTemplateService
import kr.co.shoppingcart.cart.domain.template.services.DeleteTemplateService
import kr.co.shoppingcart.cart.domain.template.services.GetTemplateService
import kr.co.shoppingcart.cart.domain.template.services.UpdateTemplateService
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.fixture.TemplateFixture.DEFAULT_USER_ID
import kr.co.shoppingcart.cart.fixture.TemplateFixture.PRIVATE
import kr.co.shoppingcart.cart.fixture.TemplateFixture.PUBLIC
import kr.co.shoppingcart.cart.mock.vo.MockBasket
import kr.co.shoppingcart.cart.mock.vo.MockPermissions
import kr.co.shoppingcart.cart.mock.vo.MockTemplate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyLong
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
    private lateinit var getBasketService: GetBasketService

    @Mock
    private lateinit var createTemplateService: CreateTemplateService

    @Mock
    private lateinit var getTemplateService: GetTemplateService

    @Mock
    private lateinit var deleteTemplateService: DeleteTemplateService

    @Mock
    private lateinit var updateTemplateService: UpdateTemplateService

    @Mock
    private lateinit var ownerPermissionService: OwnerPermissionService

    @Mock
    private lateinit var readerPermissionService: ReaderPermissionService

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
        private var createTemplateCommand =
            CreateTemplateCommand(
                name = "test",
                userId = 1,
            )

        @Test
        fun `정상적으로 생성된다`() {
            `when`(
                createTemplateService.create(
                    name = createTemplateCommand.name,
                    userId = createTemplateCommand.userId,
                ),
            ).thenReturn(
                MockTemplate.getTemplateByCreate(createTemplateCommand),
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val result =
                templateUseCase.createByApi(createTemplateCommand)

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
                ownerPermissionService.getByUserIdAndTemplateId(
                    userId = defaultCommand.userId,
                    templateId = defaultCommand.id,
                ),
            ).thenReturn(
                null,
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val exception =
                org.junit.jupiter.api.assertThrows<CustomException> {
                    templateUseCase.updateSharedById(defaultCommand)
                }

            assertEquals(ExceptionCode.E_403_000.name, exception.code.name)
        }

        @Test
        fun `반환값은 Template`() {
            `when`(
                ownerPermissionService.getByUserIdAndTemplateId(
                    userId = defaultCommand.userId,
                    templateId = defaultCommand.id,
                ),
            ).thenReturn(
                MockPermissions.getPermission(1, 0),
            )

            `when`(
                updateTemplateService.updateSharedById(
                    id = defaultCommand.id,
                    isShared = true,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(defaultCommand.id),
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val result =
                templateUseCase.updateSharedById(defaultCommand)

            assertInstanceOf(Template::class.java, result)
        }
    }

    @Nested
    @DisplayName("copyOwnTemplateInComplete test")
    inner class CopyOwnTemplateInCompleteTest {
        private var defaultCommand =
            CopyTemplateInCompleteCommand(
                id = 1,
                userId = 1,
            )

        @Test
        fun `본인의 tempalte이 아니라면 에러`() {
            `when`(
                ownerPermissionService.getByUserIdAndTemplateId(
                    userId = defaultCommand.userId,
                    templateId = defaultCommand.id,
                ),
            ).thenReturn(
                null,
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val exception =
                org.junit.jupiter.api.assertThrows<CustomException> {
                    templateUseCase.copyOwnTemplateInComplete(defaultCommand)
                }

            assertEquals(ExceptionCode.E_403_000.name, exception.code.name)
        }

        @Test
        fun `basket이 없으면 bulksave 호출 X`() {
            `when`(
                ownerPermissionService.getByUserIdAndTemplateId(
                    userId = defaultCommand.userId,
                    templateId = defaultCommand.id,
                ),
            ).thenReturn(
                MockPermissions.getPermission(1, 0),
            )

            `when`(
                getTemplateService.getByIdOrFail(
                    id = defaultCommand.id,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(defaultCommand.id),
            )

            `when`(
                createTemplateService.create(
                    name = "test",
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(2),
            )

            `when`(
                getBasketService.getByTemplateId(
                    defaultCommand.id,
                ),
            ).thenReturn(
                emptyList(),
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            templateUseCase.copyOwnTemplateInComplete(defaultCommand)

            verify(createTemplateService, times(0)).createNewBasketsByTemplateId(anyList(), anyLong())
        }

        @Test
        fun `check 안된 basket이 없으면 bulksave 호출 X`() {
            `when`(
                ownerPermissionService.getByUserIdAndTemplateId(
                    userId = defaultCommand.userId,
                    templateId = defaultCommand.id,
                ),
            ).thenReturn(
                MockPermissions.getPermission(1, 0),
            )

            `when`(
                getTemplateService.getByIdOrFail(
                    id = defaultCommand.id,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(defaultCommand.id),
            )

            `when`(
                createTemplateService.create(
                    name = "test",
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(2),
            )

            `when`(
                getBasketService.getByTemplateId(
                    defaultCommand.id,
                ),
            ).thenReturn(
                MockBasket.getBasketsAllChecked(),
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val result = templateUseCase.copyOwnTemplateInComplete(defaultCommand)

            assertEquals(MockTemplate.getTemplate(2), result)
            verify(createTemplateService, times(0)).createNewBasketsByTemplateId(anyList(), anyLong())
        }

        @Test
        fun `체크 안된 basket 있으면 저장`() {
            `when`(
                ownerPermissionService.getByUserIdAndTemplateId(
                    userId = defaultCommand.userId,
                    templateId = defaultCommand.id,
                ),
            ).thenReturn(
                MockPermissions.getPermission(1, 0),
            )

            `when`(
                getTemplateService.getByIdOrFail(
                    id = defaultCommand.id,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(defaultCommand.id),
            )

            `when`(
                createTemplateService.create(
                    name = "test",
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(2),
            )

            `when`(
                getBasketService.getByTemplateId(
                    defaultCommand.id,
                ),
            ).thenReturn(
                MockBasket.getBasketsAllNonChecked(),
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val result = templateUseCase.copyOwnTemplateInComplete(defaultCommand)

            assertEquals(MockTemplate.getTemplate(2), result)
            verify(createTemplateService, times(1)).createNewBasketsByTemplateId(anyList(), anyLong())
        }
    }

    @Nested
    @DisplayName("copyOwnTemplate test")
    inner class CopyOwnTemplateTest {
        private var defaultCommand =
            CopyOwnTemplateCommand(
                id = 1,
                userId = 1,
            )

        @Test
        fun `템플릿이 본인 것이 아닐 때 에러`() {
            `when`(
                ownerPermissionService.getByUserIdAndTemplateId(
                    userId = defaultCommand.userId,
                    templateId = defaultCommand.id,
                ),
            ).thenReturn(null)

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val exception =
                org.junit.jupiter.api.assertThrows<CustomException> {
                    templateUseCase.copyOwnTemplate(defaultCommand)
                }

            assertEquals(ExceptionCode.E_403_000.name, exception.code.name)
        }

        @Test
        fun `템플릿에 basket이 없을 경우 bulksave 호출 x`() {
            `when`(
                ownerPermissionService.getByUserIdAndTemplateId(
                    userId = defaultCommand.userId,
                    templateId = defaultCommand.id,
                ),
            ).thenReturn(
                MockPermissions.getPermission(1, 0),
            )

            `when`(
                getTemplateService.getByIdOrFail(
                    id = defaultCommand.id,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(defaultCommand.id),
            )

            `when`(
                createTemplateService.create(
                    name = MockTemplate.getTemplate(defaultCommand.id).name.name,
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(2),
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val result = templateUseCase.copyOwnTemplate(defaultCommand)

            assertEquals(MockTemplate.getTemplate(2), result)
            verify(createTemplateService, times(0)).createNewBasketsByTemplateId(anyList(), anyLong())
        }
    }

    @Nested
    @DisplayName("copyTemplate test")
    inner class CopyTemplateTest {
        private var defaultCommand =
            CopyTemplateCommand(
                id = 1,
                userId = 1,
            )

        @Test
        fun `템플릿이 퍼블릭이 아닐 경우 에러`() {
            `when`(
                getTemplateService.getByIdOrFail(
                    id = defaultCommand.id,
                ),
            ).thenReturn(
                MockTemplate.getTemplateByPublic(defaultCommand.id, PRIVATE),
            )
            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val exception =
                org.junit.jupiter.api.assertThrows<CustomException> {
                    templateUseCase.copyTemplate(defaultCommand)
                }

            assertEquals(ExceptionCode.E_403_001.name, exception.code.name)
        }

        @Test
        fun `반환값 template`() {
            `when`(
                getTemplateService.getByIdOrFail(
                    id = defaultCommand.id,
                ),
            ).thenReturn(
                MockTemplate.getTemplateByPublic(defaultCommand.id, PUBLIC),
            )
            `when`(
                createTemplateService.create(
                    name = MockTemplate.getTemplate(defaultCommand.id).name.name,
                    userId = defaultCommand.userId,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(2),
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val copyTemplate = templateUseCase.copyTemplate(defaultCommand)

            assertEquals(MockTemplate.getTemplate(2), copyTemplate)
        }
    }

    @Nested
    @DisplayName("getDefaultNameByUserId test")
    inner class GetDefaultNameByUserIdTest {
        @Test
        fun `장바구니N으로 저장된 내용이 있을 때 +1된 값으로 반환한다`() {
            `when`(
                getTemplateService.getByUserIdForDefaultName(
                    userId = DEFAULT_USER_ID,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(1),
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val result = templateUseCase.getDefaultNameByUserId(DEFAULT_USER_ID)

            assertEquals(2, result)
        }

        @Test
        fun `기본값으로 되어있는 장바구니가 없을 때 기본값으로 반환한다`() {
            `when`(
                getTemplateService.getByUserIdForDefaultName(
                    userId = DEFAULT_USER_ID,
                ),
            ).thenReturn(
                null,
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val result = templateUseCase.getDefaultNameByUserId(DEFAULT_USER_ID)

            assertEquals(1, result)
        }

        @Test
        fun `장바구니N이 2자리 이상이여도 가능하다`() {
            `when`(
                getTemplateService.getByUserIdForDefaultName(
                    userId = DEFAULT_USER_ID,
                ),
            ).thenReturn(
                MockTemplate.getTemplate(1, "장바구니100"),
            )

            templateUseCase =
                TemplateUseCase(
                    getBasketService,
                    createTemplateService,
                    getTemplateService,
                    deleteTemplateService,
                    updateTemplateService,
                    ownerPermissionService,
                    readerPermissionService,
                )

            val result = templateUseCase.getDefaultNameByUserId(DEFAULT_USER_ID)

            assertEquals(101, result)
        }
    }
}
