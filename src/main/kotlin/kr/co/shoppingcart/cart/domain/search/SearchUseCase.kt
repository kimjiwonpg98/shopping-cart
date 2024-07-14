package kr.co.shoppingcart.cart.domain.search

import kr.co.shoppingcart.cart.domain.search.command.SearchForKeywordCommand
import org.springframework.stereotype.Service

@Service
class SearchUseCase(
    private val searchRepository: SearchRepository,
) {
    fun findForKeyword(searchForKeywordCommand: SearchForKeywordCommand) =
        searchRepository.findByKeyword(
            searchForKeywordCommand.keyword,
        )
}
