package kr.co.shoppingcart.cart.api.basket.dto.out

import kr.co.shoppingcart.cart.api.category.out.CategoryResponse
import java.time.LocalDateTime

data class BasketResponse(
    val name: String,
    val checked: Boolean,
    val count: Long,
    val createTime: LocalDateTime,
    val updateTime: LocalDateTime,
    val category: CategoryResponse,
)
