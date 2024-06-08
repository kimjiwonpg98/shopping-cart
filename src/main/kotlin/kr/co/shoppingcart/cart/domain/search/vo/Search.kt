package kr.co.shoppingcart.cart.domain.search.vo

data class Search(
    val category: SearchCategory,
    val name: SearchName
) {
    companion object {
        fun convertToDomain(name: String, category: String): Search =
            Search (
                name = SearchName(name),
                category = SearchCategory(category)
            )
    }
}
