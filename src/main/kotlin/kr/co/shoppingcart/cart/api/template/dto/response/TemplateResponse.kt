package kr.co.shoppingcart.cart.api.template.dto.response

import java.time.ZonedDateTime

data class TemplateResponse(
    val id: Long,
    val name: String,
    val userId: Long,
    val createTime: ZonedDateTime,
    val updateTime: ZonedDateTime,
)
