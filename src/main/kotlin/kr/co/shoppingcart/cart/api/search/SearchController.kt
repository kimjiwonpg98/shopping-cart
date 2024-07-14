package kr.co.shoppingcart.cart.api.search

import jakarta.validation.Valid
import kr.co.shoppingcart.cart.api.search.dto.request.SearchRequestDto
import kr.co.shoppingcart.cart.api.search.dto.response.SearchResponse
import kr.co.shoppingcart.cart.api.search.dto.response.SearchResponseDto
import kr.co.shoppingcart.cart.domain.search.SearchUseCase
import kr.co.shoppingcart.cart.domain.search.command.SearchForKeywordCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchController(
    private val searchUseCase: SearchUseCase,
) {
    @GetMapping("/search")
    fun search(
        @Valid @ModelAttribute
        searchRequestDto: SearchRequestDto,
    ): ResponseEntity<SearchResponseDto> {
        val keywordCommand =
            SearchForKeywordCommand(
                keyword = searchRequestDto.keyword,
            )

        val responseBody =
            SearchResponseDto(
                result =
                    searchUseCase.findForKeyword(keywordCommand).map {
                        SearchResponse(
                            it.name.name,
                            it.category.category,
                        )
                    },
            )

        return ResponseEntity.status(HttpStatus.OK).body(responseBody)
    }
}
