package kr.co.shoppingcart.cart.domain.search.vo

data class SearchResult(
    val category: SearchCategory,
    val name: SearchName,
) {
    companion object {
        fun convertToDomain(
            name: String,
            category: String,
        ): SearchResult =
            SearchResult(
                name = SearchName(name),
                category = SearchCategory(category),
            )
    }
}
