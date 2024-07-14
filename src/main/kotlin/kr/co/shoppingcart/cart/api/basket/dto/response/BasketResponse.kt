package kr.co.shoppingcart.cart.api.basket.dto.response

import kr.co.shoppingcart.cart.api.category.response.CategoryResponse
import java.time.LocalDateTime

data class BasketResponse(
    val name: String,
    val checked: Boolean,
    val count: Long,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val category: CategoryResponse,
)
