package kr.co.shoppingcart.cart.api.template.dto.request

import jakarta.validation.constraints.Null
import org.springframework.format.annotation.NumberFormat

data class GetWIthPercentRequestParamsDto(
    @field:NumberFormat
    @field:Null
    val page: String?,
    @field:Null
    @field:NumberFormat
    val size: String?,
    @field:Null
    @field:NumberFormat
    val previewCount: String?,
)
