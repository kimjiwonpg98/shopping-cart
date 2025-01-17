package kr.co.shoppingcart.cart.api.basket.dto.response

import kr.co.shoppingcart.cart.api.category.response.CategoryResponse
import kr.co.shoppingcart.cart.api.template.dto.response.TemplateResponse
import java.time.ZonedDateTime

data class BasketWithTemplateResponse(
    val id: Long,
    val name: String,
    val checked: Boolean,
    val count: Long,
    val createTime: ZonedDateTime,
    val updateTime: ZonedDateTime,
    val template: TemplateResponse,
    val category: CategoryResponse,
)
