package kr.co.shoppingcart.cart.api.category

import io.swagger.v3.oas.annotations.Operation
import kr.co.shoppingcart.cart.api.category.response.GetCategoriesResDto
import kr.co.shoppingcart.cart.domain.category.CategoryUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class CategoryController(
    private val categoryUseCase: CategoryUseCase,
) {
    @Operation(summary = "카테고리 전체 리스트 조회", description = "DB에 적재되어있는 값")
    @GetMapping("/v1/categories")
    fun getCategories(): ResponseEntity<GetCategoriesResDto> =
        ResponseEntity
            .status(200)
            .body(
                GetCategoriesResDto(
                    result =
                        categoryUseCase.getAll().map(CategoryResponseMapper::toResponse),
                ),
            )

    @Operation(summary = "장바구니 리스트에 존재하는 카테고리만 조회", description = "장바구니 리스트에 존재하는 카테고리만 조회")
    @GetMapping("/v1/template/{templateId}/categories")
    fun getCategoriesByTemplateId(
        @PathVariable templateId: Long,
    ): ResponseEntity<GetCategoriesResDto> =
        ResponseEntity
            .status(200)
            .body(
                GetCategoriesResDto(
                    result =
                        categoryUseCase
                            .getByTemplateId(
                                templateId = templateId,
                            ).map(CategoryResponseMapper::toResponse),
                ),
            )
}
