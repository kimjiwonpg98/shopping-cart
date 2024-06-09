package kr.co.shoppingcart.cart.domain.search

import kr.co.shoppingcart.cart.domain.search.vo.SearchResult

interface SearchRepository {
    fun findByKeyword(keyword: String): List<SearchResult>
}