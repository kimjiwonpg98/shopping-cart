package kr.co.shoppingcart.cart.usecase

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketByTemplateIdAndCategoryIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import kr.co.shoppingcart.cart.domain.basket.service.BasketCreationService
import kr.co.shoppingcart.cart.domain.basket.service.BasketUpdateService
import kr.co.shoppingcart.cart.domain.basket.service.GetBasketService
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.services.GetCategoryService
import kr.co.shoppingcart.cart.domain.permissions.services.ReaderPermissionService
import kr.co.shoppingcart.cart.domain.permissions.services.WriterPermissionService
import kr.co.shoppingcart.cart.domain.template.services.GetTemplateService
import kr.co.shoppingcart.cart.mock.vo.MockBasket
import kr.co.shoppingcart.cart.mock.vo.MockCategory
import kr.co.shoppingcart.cart.mock.vo.MockPermissions
import kr.co.shoppingcart.cart.mock.vo.MockTemplate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.BDDMockito.given
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(
    MockitoExtension::class,
)
class BasketUseCaseTest {
    @Mock
    private lateinit var getCategoryService: GetCategoryService

    @Mock
    private lateinit var getBasketService: GetBasketService

    @Mock
    private lateinit var basketUpdateService: BasketUpdateService

    @Mock
    private lateinit var basketCreationService: BasketCreationService

    @Mock
    private lateinit var getTemplateService: GetTemplateService

    @Mock
    private lateinit var writerPermissionService: WriterPermissionService

    @Mock
    private lateinit var readerPermissionService: ReaderPermissionService

    @InjectMocks
    private lateinit var basketUseCase: BasketUseCase

    @BeforeEach
    fun setUp() {
        // Mockito 초기화
        MockitoAnnotations.openMocks(this)
    }

    @Nested
    @DisplayName("create() test")
    inner class SaveTest {
        var command =
            CreateBasketCommand(
                templateId = 1L,
                name = "test",
                categoryName = "기타",
                userId = 1,
                count = 1,
            )

        @Test
        fun `생성 시 템플릿이 없으면 에러를 뱉는다`() {
            given(getCategoryService.getByNameOrBasic("기타")).willReturn(
                MockCategory.getCategory(1),
            )

            `when`(getTemplateService.getByIdOrFail(command.templateId)).thenThrow(
                CustomException.responseBody(ExceptionCode.E_404_001),
            )

            basketUseCase =
                BasketUseCase(
                    getCategoryService,
                    getBasketService,
                    basketUpdateService,
                    basketCreationService,
                    getTemplateService,
                    writerPermissionService,
                    readerPermissionService,
                )

            val exception =
                assertThrows<CustomException> {
                    basketUseCase.create(
                        command,
                    )
                }

            assertEquals(ExceptionCode.E_404_001.name, exception.code.name)
        }

        @Test
        fun `정상적으로 저장될 시 반환값은 Basket 객체를 반환한다`() {
            val mockCategory = MockCategory.getCategory(1)
            val mockBasket = MockBasket.getBasketByCreate(command)

            given(
                getCategoryService.getByNameOrBasic(command.categoryName),
            ).willReturn(mockCategory)
            given(
                getTemplateService.getByIdOrFail(command.templateId),
            ).willReturn(MockTemplate.getTemplate(command.templateId))

            `when`(
                writerPermissionService.getOverLevelByUserIdAndTemplateId(command.userId, command.templateId),
            ).thenReturn(
                MockPermissions.getOptionalPermission(1, true),
            )

            given(
                basketCreationService.save(
                    mockBasket,
                ),
            ).willReturn(MockBasket.getBasketByCreate(command))

            basketUseCase =
                BasketUseCase(
                    getCategoryService,
                    getBasketService,
                    basketUpdateService,
                    basketCreationService,
                    getTemplateService,
                    writerPermissionService,
                    readerPermissionService,
                )

            val result =
                basketUseCase.create(
                    command,
                )

            assertThat(result).isNotNull
            assertThat(result).isEqualTo(MockBasket.getBasketByCreate(command))
            assertInstanceOf(Basket::class.java, result)
        }
    }

    @Nested
    @DisplayName("updateIsAddedByFlagAndId test")
    inner class UpdateIsAddedByFlagAndIdTest {
        @Test
        fun `물품이 없을 시 에러 발생`() {
            `when`(getBasketService.getByIdOrFail(anyLong())).thenThrow(
                CustomException.responseBody(ExceptionCode.E_404_002),
            )

            basketUseCase =
                BasketUseCase(
                    getCategoryService,
                    getBasketService,
                    basketUpdateService,
                    basketCreationService,
                    getTemplateService,
                    writerPermissionService,
                    readerPermissionService,
                )

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
        fun `권한이 없을 떄 에러 발생`() {
            `when`(getBasketService.getByIdOrFail(1)).thenReturn(
                MockBasket.getBasket(1, true),
            )

            `when`(writerPermissionService.getOverLevelByUserIdAndTemplateId(1L, 1)).thenReturn(
                null,
            )

            basketUseCase =
                BasketUseCase(
                    getCategoryService,
                    getBasketService,
                    basketUpdateService,
                    basketCreationService,
                    getTemplateService,
                    writerPermissionService,
                    readerPermissionService,
                )

            val exception =
                assertThrows<CustomException> {
                    basketUseCase.updateIsAddedByFlagAndId(
                        UpdateBasketFlagCommand(
                            userId = 1L,
                            basketId = 1L,
                            checked = true,
                        ),
                    )
                }

            assertEquals(ExceptionCode.E_403_000.name, exception.code.name)
        }

        @Test
        fun `권한이 writer 미만일 떄 에러 발생`() {
            `when`(getBasketService.getByIdOrFail(1)).thenReturn(
                MockBasket.getBasket(1, true),
            )

            `when`(writerPermissionService.getOverLevelByUserIdAndTemplateId(1L, 1)).thenReturn(
                null,
            )

            basketUseCase =
                BasketUseCase(
                    getCategoryService,
                    getBasketService,
                    basketUpdateService,
                    basketCreationService,
                    getTemplateService,
                    writerPermissionService,
                    readerPermissionService,
                )

            val exception =
                assertThrows<CustomException> {
                    basketUseCase.updateIsAddedByFlagAndId(
                        UpdateBasketFlagCommand(
                            userId = 1L,
                            basketId = 1L,
                            checked = true,
                        ),
                    )
                }

            assertEquals(ExceptionCode.E_403_000.name, exception.code.name)
        }

        @Test
        fun `정상적으로 저장될 시 반환값은 Basket 객체를 반환한다`() {
            `when`(getBasketService.getByIdOrFail(1)).thenReturn(
                MockBasket.getBasket(1, false),
            )

            `when`(writerPermissionService.getOverLevelByUserIdAndTemplateId(1L, 1)).thenReturn(
                MockPermissions.getPermission(1, 0),
            )

            `when`(basketUpdateService.updateCheckedById(1, true)).thenReturn(
                MockBasket.getBasket(1, true),
            )

            basketUseCase =
                BasketUseCase(
                    getCategoryService,
                    getBasketService,
                    basketUpdateService,
                    basketCreationService,
                    getTemplateService,
                    writerPermissionService,
                    readerPermissionService,
                )

            val result =
                basketUseCase.updateIsAddedByFlagAndId(
                    UpdateBasketFlagCommand(
                        userId = 1L,
                        basketId = 1L,
                        checked = true,
                    ),
                )

            assertInstanceOf(Basket::class.java, result)
        }
    }

    @Nested
    @DisplayName("getByTemplateIdAndCategoryId() test")
    inner class GetByTemplateIdAndCategoryIdTest {
        var command =
            GetBasketByTemplateIdAndCategoryIdCommand(
                templateId = 1L,
                categoryId = 1L,
                userId = 1,
            )

        @Test
        fun `조회 시 없으면 빈 list`() {
            `when`(
                getBasketService.getByTemplateIdAndCategoryIdByUpdatedDesc(command.templateId, command.categoryId),
            ).thenReturn(
                emptyList(),
            )
            `when`(
                readerPermissionService.getOverLevelByUserIdAndTemplateId(command.userId, command.templateId),
            ).thenReturn(
                MockPermissions.getPermissionWriter(1, true),
            )

            basketUseCase =
                BasketUseCase(
                    getCategoryService,
                    getBasketService,
                    basketUpdateService,
                    basketCreationService,
                    getTemplateService,
                    writerPermissionService,
                    readerPermissionService,
                )

            val result =
                basketUseCase.getByTemplateIdAndCategoryId(
                    command,
                )

            assertThat(result).isEmpty()
        }

        @Test
        fun `정상적으로 조회했을 떄 확인`() {
            `when`(
                getBasketService.getByTemplateIdAndCategoryIdByUpdatedDesc(command.templateId, command.categoryId),
            ).thenReturn(
                emptyList(),
            )

            `when`(
                readerPermissionService.getOverLevelByUserIdAndTemplateId(command.userId, command.templateId),
            ).thenReturn(
                MockPermissions.getPermissionReader(1),
            )

            basketUseCase =
                BasketUseCase(
                    getCategoryService,
                    getBasketService,
                    basketUpdateService,
                    basketCreationService,
                    getTemplateService,
                    writerPermissionService,
                    readerPermissionService,
                )

            val result =
                basketUseCase.getByTemplateIdAndCategoryId(
                    command,
                )

            assertThat(result).isEmpty()
        }
    }
}
