package kr.co.shoppingcart.cart.api.search.dto

import jakarta.validation.constraints.NotBlank

data class SearchRequestDto(
    @field:NotBlank(message = "Keyword must not be blank")
    val keyword: String
)