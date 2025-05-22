package kr.co.shoppingcart.cart.domain.template.command

data class UnpinnedTemplateCommand(
    val templateId: Long,
    val userId: Long,
)
