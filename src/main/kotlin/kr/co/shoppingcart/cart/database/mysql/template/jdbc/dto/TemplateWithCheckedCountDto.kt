package kr.co.shoppingcart.cart.database.mysql.template.jdbc.dto

import java.time.ZonedDateTime

data class TemplateWithCheckedCountDto(
    val checkedCount: Long,
    val unCheckedCount: Long,
    val id: Long,
    val name: String,
    val userId: Long,
    val isPublic: Boolean,
    val createdAt: ZonedDateTime,
    val updatedAt: ZonedDateTime,
)
