package kr.co.shoppingcart.cart.api.search.adapter.input.web

import jakarta.validation.Valid
import kr.co.shoppingcart.cart.api.search.adapter.input.web.dto.SearchRequest
import kr.co.shoppingcart.cart.api.search.adapter.input.web.dto.SearchResponse
import kr.co.shoppingcart.cart.api.search.adapter.input.web.dto.SearchResponseBody
import kr.co.shoppingcart.cart.core.search.application.port.input.SearchByKeyword
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController(
    private val searchByKeyword: SearchByKeyword,
) {
    @GetMapping("/v1/search")
    fun search(
        @Valid @ModelAttribute
        searchRequest: SearchRequest,
    ): ResponseEntity<SearchResponse> {
        val responseBody =
            SearchResponse(
                result =
                    searchByKeyword.search(searchRequest.keyword).map {
                        SearchResponseBody(
                            it.name,
                            it.category,
                        )
                    },
            )

        return ResponseEntity.status(HttpStatus.OK).body(responseBody)
    }
}
