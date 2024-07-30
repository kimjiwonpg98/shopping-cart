package kr.co.shoppingcart.cart.api.basket.dto.response

import kr.co.shoppingcart.cart.api.category.response.CategoryResponse
import java.time.ZonedDateTime

data class BasketResponse(
    val name: String,
    val checked: Boolean,
    val count: Long,
    val createTime: ZonedDateTime,
    val updateTime: ZonedDateTime,
    val category: CategoryResponse,
)
