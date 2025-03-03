@file:Suppress("ktlint:standard:filename")

package kr.co.shoppingcart.cart.api.basket.dto

import jakarta.validation.constraints.NotBlank
import kr.co.shoppingcart.cart.domain.basket.command.GetBasketByTemplateIdAndCategoryNameCommand
import kr.co.shoppingcart.cart.domain.basket.command.GetPublicBasketByTemplateIdAndCategoryNameCommand
import org.springframework.format.annotation.NumberFormat

data class GetByTemplateIdAndCategoryNameReqQuery(
    @field:NumberFormat
    val templateId: String,
    @field:NotBlank
    val categoryName: String,
) {
    fun of(userId: Long): GetBasketByTemplateIdAndCategoryNameCommand =
        GetBasketByTemplateIdAndCategoryNameCommand(
            templateId = templateId.toLong(),
            categoryName = categoryName,
            userId = userId,
        )

    fun of(): GetPublicBasketByTemplateIdAndCategoryNameCommand =
        GetPublicBasketByTemplateIdAndCategoryNameCommand(
            templateId = templateId.toLong(),
            categoryName = categoryName,
        )
}
