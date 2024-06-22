package kr.co.shoppingcart.cart.domain.category.vo

data class Category(
    val id: CategoryId,
    val name: CategoryName
) {
    companion object {
        fun toDomain(id: Long, name: String) =
            Category(
                id = CategoryId(id),
                name = CategoryName(name)
            )
    }
}
