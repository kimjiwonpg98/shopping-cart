package kr.co.shoppingcart.cart.domain.basket.vo

import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template
import java.time.ZonedDateTime

data class Basket(
    val id: BasketId,
    val name: BasketName,
    var checked: BasketChecked,
    val count: BasketCount,
    val categoryName: BasketCategoryName,
    val templateId: BasketTemplateId?,
    val categoryId: BasketCategoryId?,
    val createTime: BasketCreatedAt?,
    val updateTime: BasketUpdatedAt?,
    val category: Category,
    val template: Template?,
) {
    companion object {
        fun toDomain(
            id: Long,
            name: String,
            checked: Boolean,
            count: Long,
            categoryName: String,
            category: Category,
            template: Template?,
            templateId: Long?,
            categoryId: Long?,
            createdTime: ZonedDateTime?,
            updatedTime: ZonedDateTime?,
        ): Basket =
            Basket(
                id = BasketId(id),
                name = BasketName(name),
                checked = BasketChecked(checked),
                count = BasketCount(count),
                categoryName = BasketCategoryName(categoryName),
                category = category,
                template = template,
                createTime = createdTime?.let { BasketCreatedAt(it) },
                updateTime = updatedTime?.let { BasketUpdatedAt(it) },
                templateId = templateId?.let { BasketTemplateId(it) },
                categoryId = categoryId?.let { BasketCategoryId(it) },
            )
    }
}
