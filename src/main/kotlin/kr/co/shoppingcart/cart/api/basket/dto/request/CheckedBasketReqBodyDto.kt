package kr.co.shoppingcart.cart.api.basket.dto.request

import jakarta.validation.constraints.NotBlank
import org.springframework.format.annotation.NumberFormat

data class CheckedBasketReqBodyDto(
    @field:NotBlank
    val checked: Boolean,
    @field:NumberFormat
    val basketId: Long,
)
