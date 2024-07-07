package kr.co.shoppingcart.cart.domain.basket.vo

import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template
import java.time.LocalDateTime

data class Basket(
    val name: BasketName,
    val checked: BasketChecked,
    val count: BasketCount,
    val createTime: BasketCreatedAt?,
    val updateTime: BasketUpdatedAt?,
    val category: Category,
    val template: Template,
) {
    companion object {
        fun toDomain(
            name: String,
            checked: Boolean,
            count: Long,
            category: Category,
            template: Template,
            createTime: LocalDateTime?,
            updateTime: LocalDateTime?,
        ): Basket =
            Basket(
                name = BasketName(name),
                checked = BasketChecked(checked),
                createTime = createTime?.let { BasketCreatedAt(it) },
                updateTime = updateTime?.let { BasketUpdatedAt(it) },
                count = BasketCount(count),
                category = category,
                template = template
            )
    }
}
