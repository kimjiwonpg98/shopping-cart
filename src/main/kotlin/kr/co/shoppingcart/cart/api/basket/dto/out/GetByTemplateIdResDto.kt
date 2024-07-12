package kr.co.shoppingcart.cart.api.basket.dto.out

data class GetByTemplateIdResDto (
    val nonChecked: List<BasketResponse>,
    val checked: List<BasketResponse>,
)