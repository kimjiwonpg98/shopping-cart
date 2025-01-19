package kr.co.shoppingcart.cart.api.basket

import io.swagger.v3.oas.annotations.Operation
import jakarta.validation.Valid
import kr.co.shoppingcart.cart.api.basket.dto.request.CheckedAllBasketReqBodyDto
import kr.co.shoppingcart.cart.api.basket.dto.request.CheckedBasketReqBodyDto
import kr.co.shoppingcart.cart.api.basket.dto.request.CreateBasketReqBodyDto
import kr.co.shoppingcart.cart.api.basket.dto.request.GetByTemplateIdAndCategoryIdReqQueryDto
import kr.co.shoppingcart.cart.api.basket.dto.request.GetByTemplateIdReqDto
import kr.co.shoppingcart.cart.api.basket.dto.request.UpdateBasketContentReqBodyDto
import kr.co.shoppingcart.cart.api.basket.dto.response.CreateBasketResDto
import kr.co.shoppingcart.cart.api.basket.dto.response.GetByIdResponseDto
import kr.co.shoppingcart.cart.api.basket.dto.response.GetByTemplateIdAndCategoryIdResDto
import kr.co.shoppingcart.cart.api.basket.dto.response.GetByTemplateIdResDto
import kr.co.shoppingcart.cart.api.basket.dto.response.UpdateBasketContentResDto
import kr.co.shoppingcart.cart.auth.JwtPayload
import kr.co.shoppingcart.cart.auth.annotation.CurrentUser
import kr.co.shoppingcart.cart.common.error.annotations.OpenApiSpecApiException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.command.DeleteBasketByIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketByIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketByTemplateIdAndCategoryIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketsByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateAllCheckedCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketContentCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class BasketController(
    private val basketUseCase: BasketUseCase,
) {
    @OpenApiSpecApiException(
        [
            ExceptionCode.E_403_000,
            ExceptionCode.E_400_000,
            ExceptionCode.E_404_003,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "장바구니 추가", description = "장바구니 추가 시 카테고리와 이름, 개수를 입력")
    @PostMapping("/v1/basket")
    fun save(
        @RequestBody body: CreateBasketReqBodyDto,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<CreateBasketResDto> {
        val basket =
            CreateBasketCommand(
                templateId = body.templateId,
                name = body.name,
                categoryName = body.categoryName,
                count = body.count,
                userId = currentUser.identificationValue.toLong(),
            )
        val result = basketUseCase.create(basket)
        return ResponseEntity
            .status(201)
            .body(
                CreateBasketResDto(
                    result = result.let(BasketResponseMapper::toResponse),
                ),
            )
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_403_000,
            ExceptionCode.E_404_002,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "물품 체크 수정", description = "물품의 체크 수정")
    @PutMapping("/v1/basket/check")
    fun check(
        @RequestBody body: CheckedBasketReqBodyDto,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<Unit> {
        val basket =
            UpdateBasketFlagCommand(
                userId = currentUser.identificationValue.toLong(),
                basketId = body.basketId,
                checked = body.checked,
            )

        basketUseCase.updateIsAddedByFlagAndId(basket)
        return ResponseEntity.status(200).build()
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_403_000,
            ExceptionCode.E_404_002,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "물품 체크 전체 수정", description = "물품의 전체 체크 수정")
    @PutMapping("/v1/basket/check/all")
    fun checkAll(
        @RequestBody body: CheckedAllBasketReqBodyDto,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<Unit> {
        val command =
            UpdateAllCheckedCommand(
                userId = currentUser.identificationValue.toLong(),
                templateId = body.templateId,
            )
        basketUseCase.updateAllChecked(command)
        return ResponseEntity.status(201).build()
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_403_000,
            ExceptionCode.E_404_002,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "물품 수정", description = "개수와 내용을 수정")
    @PutMapping("/v1/basket")
    fun updateBasketContent(
        @RequestBody body: UpdateBasketContentReqBodyDto,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<UpdateBasketContentResDto> {
        val command =
            UpdateBasketContentCommand(
                userId = currentUser.identificationValue.toLong(),
                content = body.content,
                count = body.count,
                basketId = body.basketId,
            )

        val basket = basketUseCase.updateBasketContent(command)
        return ResponseEntity.status(200).body(
            UpdateBasketContentResDto(
                result = basket.let(BasketResponseMapper::toResponse),
            ),
        )
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "장바구니 리스트 조회", description = "읽기 권한 이상의 유저가 조회")
    @GetMapping("/v1/baskets")
    fun getListByTemplateId(
        @ModelAttribute params: GetByTemplateIdReqDto,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<GetByTemplateIdResDto> {
        val result =
            basketUseCase.getByTemplateId(
                GetBasketsByTemplateIdCommand(
                    params.templateId.toLong(),
                    currentUser.identificationValue.toLong(),
                ),
            )

        return ResponseEntity.status(200).body(
            GetByTemplateIdResDto(
                result = result.map(BasketResponseMapper::toResponse),
            ),
        )
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "장바구니 상세 조회", description = "특정 물품 조회")
    @GetMapping("/v1/baskets/{id}")
    fun getByTemplateId(
        @Valid @PathVariable id: Long,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<GetByIdResponseDto> {
        val result =
            basketUseCase.getById(
                GetBasketByIdCommand(
                    currentUser.identificationValue.toLong(),
                    id,
                ),
            )

        return ResponseEntity.status(200).body(
            GetByIdResponseDto(
                result = result.let(BasketResponseMapper::toResponse),
            ),
        )
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "카테고리에 해당하는 물품 조회", description = "카테고리에 해당하는 물품 조회")
    @GetMapping("/v1/categories/{categoryId}/baskets")
    fun getByTemplateIdAndCategoryId(
        @ModelAttribute params: GetByTemplateIdAndCategoryIdReqQueryDto,
        @PathVariable categoryId: Long,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<GetByTemplateIdAndCategoryIdResDto> {
        val result =
            basketUseCase.getByTemplateIdAndCategoryId(
                GetBasketByTemplateIdAndCategoryIdCommand(
                    params.templateId.toLong(),
                    categoryId,
                    currentUser.identificationValue.toLong(),
                ),
            )

        return ResponseEntity.status(200).body(
            GetByTemplateIdAndCategoryIdResDto(
                result = result.map(BasketResponseMapper::toResponse),
            ),
        )
    }

    @OpenApiSpecApiException(
        [
            ExceptionCode.E_403_000,
            ExceptionCode.E_401_000,
            ExceptionCode.E_401_001,
            ExceptionCode.E_401_002,
            ExceptionCode.E_401_003,
        ],
    )
    @Operation(summary = "특정 물품 삭제", description = "특정 물품 id로 삭제")
    @DeleteMapping("/v1/baskets/{id}")
    fun deleteById(
        @PathVariable id: Long,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<Unit> {
        val command =
            DeleteBasketByIdCommand(
                basketId = id,
                userId = currentUser.identificationValue.toLong(),
            )

        basketUseCase.deleteById(command)

        return ResponseEntity.status(200).build()
    }
}
