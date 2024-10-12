package kr.co.shoppingcart.cart.domain.basket

import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template

interface BasketRepository {
    fun save(
        basket: Basket,
        template: Template,
        category: Category,
    ): Basket

    fun getByTemplateId(templateId: Long): List<Basket>

    fun getByTemplateIdWithPageNation(
        templateId: Long,
        page: Long,
        size: Long,
    ): List<Basket>

    fun getById(basketId: Long): Basket?

    fun getByTemplateIdAndSizeOrderByUpdatedDesc(
        templateId: Long,
        size: Int,
    ): List<Basket>

    fun updateCheckedById(
        basketId: Long,
        checked: Boolean,
    ): Unit

    fun bulkSave(basket: List<Basket>)
}
