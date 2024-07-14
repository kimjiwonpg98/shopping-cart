package kr.co.shoppingcart.cart.api.basket.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Null

data class CreateBasketReqBodyDto(
    @field:NotBlank
    val templateId: Long,
    @field:NotBlank
    val name: String,
    @field:NotBlank
    val categoryId: Long,
    @field:Null
    val count: Long? = 1,
)
