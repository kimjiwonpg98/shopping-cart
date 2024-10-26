package kr.co.shoppingcart.cart.api.basket

import kr.co.shoppingcart.cart.api.basket.dto.request.CheckedBasketReqBodyDto
import kr.co.shoppingcart.cart.api.basket.dto.request.CreateBasketReqBodyDto
import kr.co.shoppingcart.cart.api.basket.dto.request.GetByTemplateIdReqDto
import kr.co.shoppingcart.cart.api.basket.dto.response.CreateBasketResDto
import kr.co.shoppingcart.cart.api.basket.dto.response.GetByTemplateIdResDto
import kr.co.shoppingcart.cart.auth.JwtPayload
import kr.co.shoppingcart.cart.auth.annotation.CurrentUser
import kr.co.shoppingcart.cart.common.error.annotations.OpenApiSpecApiException
import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.domain.basket.BasketUseCase
import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketsByTemplateIdCommand
import kr.co.shoppingcart.cart.domain.basket.command.UpdateBasketFlagCommand
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
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
            ExceptionCode.E_403_000, ExceptionCode.E_400_000,
        ],
    )
    @PostMapping("/v1/basket")
    fun save(
        @RequestBody body: CreateBasketReqBodyDto,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<CreateBasketResDto> {
        val basket =
            CreateBasketCommand(
                templatedId = body.templateId,
                name = body.name,
                categoryId = body.categoryId,
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
            ExceptionCode.E_403_000, ExceptionCode.E_404_002,
        ],
    )
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
            ExceptionCode.E_403_000, ExceptionCode.E_401_000,
        ],
    )
    @GetMapping("/v1/basket")
    fun getByTemplateId(
        @ModelAttribute params: GetByTemplateIdReqDto,
        @CurrentUser currentUser: JwtPayload,
    ): ResponseEntity<GetByTemplateIdResDto> {
        val result =
            basketUseCase.getOwnByTemplateId(
                GetBasketsByTemplateIdCommand(
                    params.templateId.toLong(),
                    currentUser.identificationValue.toLong(),
                    params.page?.toLong()?.minus(1) ?: 0,
                    params.size?.toLong() ?: 10,
                ),
            )

        return ResponseEntity.status(200).body(
            GetByTemplateIdResDto(
                result = result.map(BasketResponseMapper::toResponse),
            ),
        )
    }
}
