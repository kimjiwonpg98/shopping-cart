package kr.co.shoppingcart.cart.domain.basket.vo

import kr.co.shoppingcart.cart.domain.template.vo.Template

data class BasketListAndTemplate(
    val basketList: List<Basket>,
    val template: Template,
)
