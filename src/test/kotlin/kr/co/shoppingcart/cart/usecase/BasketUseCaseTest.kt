package kr.co.shoppingcart.cart.usecase

import kr.co.shoppingcart.cart.common.error.CustomException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketRepository
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.CategoryRepository
import kr.co.shoppingcart.cart.domain.permissions.PermissionsRepository
import kr.co.shoppingcart.cart.domain.template.TemplateRepository
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
import org.mockito.ArgumentMatchers.anyBoolean
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
    private lateinit var basketRepository: BasketRepository

    @Mock
    private lateinit var categoryRepository: CategoryRepository

    @Mock
    private lateinit var templateRepository: TemplateRepository

    @Mock
    private lateinit var permissionsRepository: PermissionsRepository

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
                templatedId = 1L,
                name = "test",
                categoryId = 1L,
                userId = 1,
                count = 1,
            )

        @Test
        fun `생성 시 카테고리가 없으면 에러를 뱉는다`() {
            `when`(categoryRepository.getById(command.categoryId)).thenReturn(
                null,
            )

            basketUseCase =
                BasketUseCase(basketRepository, categoryRepository, templateRepository, permissionsRepository)

            val exception =
                assertThrows<CustomException> {
                    basketUseCase.create(
                        command,
                    )
                }

            assertEquals(ExceptionCode.E_400_000.name, exception.code.name)
        }

        @Test
        fun `생성 시 템플릿이 없으면 에러를 뱉는다`() {
            `when`(categoryRepository.getById(anyLong())).thenReturn(
                MockCategory.getCategory(anyLong()),
            )

            `when`(templateRepository.getById(command.templatedId)).thenReturn(
                null,
            )

            basketUseCase =
                BasketUseCase(basketRepository, categoryRepository, templateRepository, permissionsRepository)

            val exception =
                assertThrows<CustomException> {
                    basketUseCase.create(
                        command,
                    )
                }

            assertEquals(ExceptionCode.E_403_000.name, exception.code.name)
        }

        @Test
        fun `정상적으로 저장될 시 반환값은 Basket 객체를 반환한다`() {
            val mockTemplate = MockTemplate.getTemplate(command.templatedId)
            val mockCategory = MockCategory.getCategory(command.categoryId)
            val mockBasket = MockBasket.getBasketByCreate(command)

            given(
                categoryRepository.getById(command.categoryId),
            ).willReturn(MockCategory.getCategory(command.categoryId))
            given(
                templateRepository.getById(command.templatedId),
            ).willReturn(MockTemplate.getTemplate(command.templatedId))

            `when`(permissionsRepository.getByUserIdAndTemplateId(command.userId, command.templatedId)).thenReturn(
                MockPermissions.getPermission(1, 0),
            )

            given(
                basketRepository.save(
                    mockBasket,
                    mockTemplate,
                    mockCategory,
                ),
            ).willReturn(MockBasket.getBasketByCreate(command))

            basketUseCase =
                BasketUseCase(basketRepository, categoryRepository, templateRepository, permissionsRepository)

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
            `when`(basketRepository.getById(anyLong())).thenReturn(
                null,
            )

            basketUseCase =
                BasketUseCase(basketRepository, categoryRepository, templateRepository, permissionsRepository)

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
            `when`(basketRepository.getById(1)).thenReturn(
                MockBasket.getBasket(1),
            )

            `when`(permissionsRepository.getByUserIdAndTemplateId(1L, 1)).thenReturn(
                null,
            )

            basketUseCase =
                BasketUseCase(basketRepository, categoryRepository, templateRepository, permissionsRepository)

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
            `when`(basketRepository.getById(1)).thenReturn(
                MockBasket.getBasket(1),
            )

            `when`(permissionsRepository.getByUserIdAndTemplateId(1L, 1)).thenReturn(
                MockPermissions.getPermissionReader(1),
            )

            basketUseCase =
                BasketUseCase(basketRepository, categoryRepository, templateRepository, permissionsRepository)

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
            `when`(basketRepository.getById(1)).thenReturn(
                MockBasket.getBasket(1),
            )

            `when`(permissionsRepository.getByUserIdAndTemplateId(1L, 1)).thenReturn(
                MockPermissions.getPermission(1, 0),
            )

            `when`(basketRepository.updateCheckedById(anyLong(), anyBoolean())).thenReturn(
                MockBasket.getBasket(anyLong()),
            )

            basketUseCase =
                BasketUseCase(basketRepository, categoryRepository, templateRepository, permissionsRepository)

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
}
