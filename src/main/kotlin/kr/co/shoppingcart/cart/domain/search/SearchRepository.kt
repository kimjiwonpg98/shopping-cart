package kr.co.shoppingcart.cart.domain.search

import kr.co.shoppingcart.cart.domain.search.vo.Search

interface SearchRepository {
    fun findByKeyword(keyword: String): List<Search>
}
