package kr.co.shoppingcart.cart.domain.basket.vo

import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template
import java.time.LocalDateTime

data class Basket(
    val name: BasketName,
    val isAdded: BasketIsAdded,
    val count: BasketCount,
    val createTime: BasketCreatedAt?,
    val category: Category?,
    val template: Template?,
) {
    companion object {
        fun toDomain(name: String, isAdded: Boolean, count: Long, createTime: LocalDateTime?, category: Category?, template: Template?): Basket =
            Basket(
                name = BasketName(name),
                isAdded = BasketIsAdded(isAdded),
                createTime = createTime?.let { BasketCreatedAt(it) },
                count = BasketCount(count),
                category = category,
                template = template
            )
    }
}
