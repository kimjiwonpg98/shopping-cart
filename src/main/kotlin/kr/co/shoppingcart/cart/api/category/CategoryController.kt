package kr.co.shoppingcart.cart.api.category

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
