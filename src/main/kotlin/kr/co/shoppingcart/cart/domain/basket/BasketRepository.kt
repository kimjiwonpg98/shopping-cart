package kr.co.shoppingcart.cart.domain.basket

import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template

interface BasketRepository {
    fun save(basket: Basket, template: Template, category: Category): Unit
    fun getByTemplateId(templateId: Long): List<Basket>?
}