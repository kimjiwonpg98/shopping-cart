package kr.co.shoppingcart.cart.api.search.adapter.input.web.dto

import jakarta.validation.constraints.NotBlank

data class SearchRequest(
    @field:NotBlank(message = "Keyword must not be blank")
    val keyword: String,
)

data class SearchResponse(
    val result: List<SearchResponseBody>,
)

data class SearchResponseBody(
    val name: String,
    val category: String,
)
