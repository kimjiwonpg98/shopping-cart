package kr.co.shoppingcart.cart.api.basket.dto.response

import kr.co.shoppingcart.cart.api.template.dto.response.TemplateResponse

data class GetByPublicResDto(
    val result: List<BasketWithTemplateResponse>,
    val template: TemplateResponse,
)
