package kr.co.shoppingcart.cart.api.template.dto.response

import java.time.ZonedDateTime

data class TemplateWithPercentResponse(
    val id: Long,
    val name: String,
    val userId: Long,
    val percent: Int,
    val isPublic: Boolean,
    val createTime: ZonedDateTime,
    val updateTime: ZonedDateTime,
    val preview: List<String>,
)
