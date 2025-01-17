package kr.co.shoppingcart.cart.api.basket

import io.swagger.v3.oas.annotations.Operation
import kr.co.shoppingcart.cart.api.basket.dto.request.GetByTemplateIdAndCategoryIdReqQueryDto
import kr.co.shoppingcart.cart.api.basket.dto.request.GetByTemplateIdReqDto
import kr.co.shoppingcart.cart.api.basket.dto.response.GetByPublicResDto
import kr.co.shoppingcart.cart.api.basket.dto.response.GetByTemplateIdAndCategoryIdResDto
import kr.co.shoppingcart.cart.common.error.annotations.OpenApiSpecApiException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.basket.command.GetPublicBasketByTemplateIdAndCategoryIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetPublicBasketsByTemplateIdCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class PublicBasketController(
    private val basketUseCase: BasketUseCase,
) {
    @OpenApiSpecApiException(
        [
            ExceptionCode.E_404_001,
            ExceptionCode.E_403_001,
        ],
    )
    @Operation(summary = "공유된 장바구니 리스트 조회", description = "public인 장바구니만 조회")
    @GetMapping("/v1/baskets/public")
    fun getPublicList(
        @ModelAttribute params: GetByTemplateIdReqDto,
    ): ResponseEntity<GetByPublicResDto> {
        val result =
            basketUseCase.getPublicBasketByTemplateId(
                GetPublicBasketsByTemplateIdCommand(
                    params.templateId.toLong(),
                ),
            )

        return ResponseEntity.status(200).body(
            GetByPublicResDto(
                result = result.map(BasketResponseMapper::toPublicResponse),
            ),
        )
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_403_001,
            ExceptionCode.E_404_001,
        ],
    )
    @Operation(summary = "공유된 카테고리에 해당하는 물품 조회", description = "공유된 카테고리에 해당하는 물품 조회")
    @GetMapping("/v1/categories/{categoryId}/baskets/public")
    fun getByTemplateIdAndCategoryId(
        @ModelAttribute params: GetByTemplateIdAndCategoryIdReqQueryDto,
        @PathVariable categoryId: Long,
    ): ResponseEntity<GetByTemplateIdAndCategoryIdResDto> {
        val result =
            basketUseCase.getByTemplateIdAndCategoryIdPublic(
                GetPublicBasketByTemplateIdAndCategoryIdCommand(
                    params.templateId.toLong(),
                    categoryId,
                ),
            )

        return ResponseEntity.status(200).body(
            GetByTemplateIdAndCategoryIdResDto(
                result = result.map(BasketResponseMapper::toResponse),
            ),
        )
    }
}
