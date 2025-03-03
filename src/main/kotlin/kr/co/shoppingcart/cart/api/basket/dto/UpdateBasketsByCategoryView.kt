package kr.co.shoppingcart.cart.api.basket.dto

import kr.co.shoppingcart.cart.domain.basket.command.UpdateCategoryNameCommand

data class UpdateBasketsByCategoryReqBody(
    val categoryName: String,
    val basketIds: List<Long>,
) {
    fun toCommand(userId: Long) =
        UpdateCategoryNameCommand(
            basketIds = this.basketIds,
            categoryName = this.categoryName,
            userId = userId,
        )
}
