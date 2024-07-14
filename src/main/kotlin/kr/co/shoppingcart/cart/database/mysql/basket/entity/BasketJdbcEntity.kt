package kr.co.shoppingcart.cart.database.mysql.basket.entity

data class BasketJdbcEntity(
    val id: Long? = null,
    val content: String,
    val count: Long = 1L,
    var checked: Boolean = false,
    val isPinned: Boolean = false,
    val templateId: Long,
    val categoryId: Long,
)
