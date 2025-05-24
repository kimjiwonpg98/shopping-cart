package kr.co.shoppingcart.cart.api.template.dto.response

data class GetTemplateResponseBodyDto(
    val result: List<TemplateWithPercentAndPreviewResponse>,
    val pinned: List<TemplateWithPercentAndPreviewResponse>,
)
