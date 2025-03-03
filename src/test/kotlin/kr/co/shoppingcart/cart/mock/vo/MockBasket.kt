package kr.co.shoppingcart.cart.mock.vo

import kr.co.shoppingcart.cart.domain.basket.command.CreateBasketCommand
import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.basket.vo.BasketCategoryId
import kr.co.shoppingcart.cart.domain.basket.vo.BasketCategoryName
import kr.co.shoppingcart.cart.domain.basket.vo.BasketChecked
import kr.co.shoppingcart.cart.domain.basket.vo.BasketCount
import kr.co.shoppingcart.cart.domain.basket.vo.BasketCreatedAt
import kr.co.shoppingcart.cart.domain.basket.vo.BasketId
import kr.co.shoppingcart.cart.domain.basket.vo.BasketName
import kr.co.shoppingcart.cart.domain.basket.vo.BasketTemplateId
import kr.co.shoppingcart.cart.domain.basket.vo.BasketUpdatedAt
import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template
import java.time.ZonedDateTime

object MockBasket {
    /**
     * user는 1로 고정
     * i로 basketId만 수정
     * */
    fun getBasket(
        i: Long,
        checked: Boolean,
    ): Basket =
        Basket(
            id = BasketId(i),
            name = BasketName("test"),
            categoryId = BasketCategoryId(i),
            category =
                Category.toDomain(
                    i,
                    "test",
                ),
            count = BasketCount(i),
            checked = BasketChecked(checked),
            template = Template.toDomain(i, "template", 1L, true, 1, null, null),
            createTime = BasketCreatedAt(ZonedDateTime.now()),
            updateTime = BasketUpdatedAt(ZonedDateTime.now()),
            templateId = BasketTemplateId(i),
            categoryName = BasketCategoryName("test"),
        )

    fun getBasketsAllChecked(): List<Basket> {
        val baskets = mutableListOf<Basket>()
        for (i in 1..10) {
            baskets.add(getBasket(i.toLong(), true))
        }
        return baskets
    }

    fun getBasketsAllNonChecked(): List<Basket> {
        val baskets = mutableListOf<Basket>()
        for (i in 1..10) {
            baskets.add(getBasket(i.toLong(), false))
        }
        return baskets
    }

    fun getOptionalBasket(
        i: Long,
        flag: Boolean,
    ): Basket? = if (!flag) getBasket(i, true) else null

    fun getBasketByCreate(createBasketCommand: CreateBasketCommand): Basket =
        Basket(
            id = BasketId(0),
            name = BasketName("test"),
            categoryId = null,
            category =
                Category.toDomain(
                    1,
                    createBasketCommand.categoryName,
                ),
            categoryName = BasketCategoryName(createBasketCommand.categoryName),
            count = BasketCount(createBasketCommand.count ?: 1),
            checked = BasketChecked(false),
            template = Template.toDomain(createBasketCommand.templateId, "test", 1L, true, 1, null, null),
            createTime = null,
            updateTime = null,
            templateId = null,
        )
}
