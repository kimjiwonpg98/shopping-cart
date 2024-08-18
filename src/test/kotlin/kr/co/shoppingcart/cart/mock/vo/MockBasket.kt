package kr.co.shoppingcart.cart.mock.vo

import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.basket.vo.BasketCategoryId
import kr.co.shoppingcart.cart.domain.basket.vo.BasketChecked
import kr.co.shoppingcart.cart.domain.basket.vo.BasketCount
import kr.co.shoppingcart.cart.domain.basket.vo.BasketCreatedAt
import kr.co.shoppingcart.cart.domain.basket.vo.BasketName
import kr.co.shoppingcart.cart.domain.basket.vo.BasketTemplateId
import kr.co.shoppingcart.cart.domain.basket.vo.BasketUpdatedAt
import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template
import java.time.ZonedDateTime

object MockBasket {
    fun getBasket(i: Long): Basket =
        Basket(
            name = BasketName("test"),
            categoryId = BasketCategoryId(i),
            category =
                Category.toDomain(
                    i,
                    "test",
                ),
            count = BasketCount(i),
            checked = BasketChecked(true),
            template = Template.toDomain(i, "template", 1L, true, null, null),
            createTime = BasketCreatedAt(ZonedDateTime.now()),
            updateTime = BasketUpdatedAt(ZonedDateTime.now()),
            templateId = BasketTemplateId(i),
        )

    fun getBaskets(): List<Basket> {
        val baskets = mutableListOf<Basket>()
        for (i in 1..10) {
            baskets.add(getBasket(i.toLong()))
        }
        return baskets
    }
}
