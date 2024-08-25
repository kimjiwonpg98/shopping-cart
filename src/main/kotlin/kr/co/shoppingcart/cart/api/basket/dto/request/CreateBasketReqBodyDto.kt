package kr.co.shoppingcart.cart.api.basket.dto.request

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateBasketReqBodyDto(
    @field:Min(1)
    @field:NotNull
    val templateId: Long,
    @field:NotBlank
    @field:NotNull
    val name: String,
    @field:Min(1)
    @field:NotNull
    val categoryId: Long,
    @field:Min(1)
    val count: Long? = 1,
)
