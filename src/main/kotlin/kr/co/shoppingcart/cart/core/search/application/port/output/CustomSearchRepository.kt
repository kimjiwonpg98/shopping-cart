package kr.co.shoppingcart.cart.core.search.application.port.output

import kr.co.shoppingcart.cart.core.search.domain.CartSearch

interface CustomSearchRepository {
    fun searchForKeyword(keyword: String): List<CartSearch>
}
