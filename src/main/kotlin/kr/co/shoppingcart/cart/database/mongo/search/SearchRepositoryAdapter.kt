package kr.co.shoppingcart.cart.database.mongo.search

import kr.co.shoppingcart.cart.domain.search.SearchRepository
import kr.co.shoppingcart.cart.domain.search.vo.Search
import org.springframework.stereotype.Component

@Component
class SearchRepositoryAdapter(
    private val customSearchRepository: CustomSearchRepository,
) : SearchRepository {
    override fun findByKeyword(keyword: String): List<Search> {
        val result = customSearchRepository.searchForKeyword(keyword)
        return result.map { Search.toDomain(it.name, it.category) }
    }
}
