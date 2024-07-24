package kr.co.shoppingcart.cart.api.basket.dto.request

import org.springframework.format.annotation.NumberFormat

data class GetByTemplateIdReqDto(
    @field:NumberFormat
    var templateId: String,
    @field:NumberFormat
    val page: String?,
    @field:NumberFormat
    val size: String?,
)
