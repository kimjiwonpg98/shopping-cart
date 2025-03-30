package kr.co.shoppingcart.cart.core.search.application.port.input

import kr.co.shoppingcart.cart.core.search.domain.CartSearch

interface SearchByKeyword {
    fun search(keyword: String): List<CartSearch>
}
