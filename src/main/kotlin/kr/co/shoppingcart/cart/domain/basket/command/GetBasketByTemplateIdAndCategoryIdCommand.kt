package kr.co.shoppingcart.cart.domain.basket.command

import jakarta.validation.constraints.NotNull

data class GetBasketByTemplateIdAndCategoryIdCommand(
    @field:NotNull
    val templateId: Long,
    @field:NotNull
    val categoryId: Long,
    @field:NotNull
    val userId: Long,
)
