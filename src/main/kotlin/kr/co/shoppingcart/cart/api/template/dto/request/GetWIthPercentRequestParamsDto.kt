package kr.co.shoppingcart.cart.api.template.dto.request

import org.springframework.format.annotation.NumberFormat

data class GetWIthPercentRequestParamsDto(
    @field:NumberFormat
    val previewCount: String?,
)
