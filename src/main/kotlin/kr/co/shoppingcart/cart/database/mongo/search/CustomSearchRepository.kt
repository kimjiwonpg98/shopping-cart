package kr.co.shoppingcart.cart.database.mongo.search

import kr.co.shoppingcart.cart.database.mongo.search.entity.CartSearchEntity

interface CustomSearchRepository {
    fun searchForKeyword(keyword: String): List<CartSearchEntity>
}
