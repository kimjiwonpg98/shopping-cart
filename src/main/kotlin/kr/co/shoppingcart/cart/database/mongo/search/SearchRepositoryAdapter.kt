package kr.co.shoppingcart.cart.database.mongo.search

import kr.co.shoppingcart.cart.domain.search.SearchRepository
import kr.co.shoppingcart.cart.domain.search.vo.SearchResult
import org.springframework.stereotype.Component

@Component
class SearchRepositoryAdapter(
    private val customSearchRepository: CustomSearchRepository,
) : SearchRepository {
    override fun findByKeyword(keyword: String): List<SearchResult> {
        val result = customSearchRepository.searchForKeyword(keyword)
        return result.map { SearchResult.convertToDomain(it.name, it.category) }
    }
}
