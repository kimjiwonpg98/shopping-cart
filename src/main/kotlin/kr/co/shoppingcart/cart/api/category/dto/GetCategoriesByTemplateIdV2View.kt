@file:Suppress("ktlint:standard:filename")

package kr.co.shoppingcart.cart.api.category.dto

data class GetCategoriesByTemplateIdV2ResDto(
    val categories: List<String>,
)
