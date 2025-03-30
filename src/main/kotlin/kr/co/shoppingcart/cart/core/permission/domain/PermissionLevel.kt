package kr.co.shoppingcart.cart.core.permission.domain

enum class PermissionLevel(
    val level: Int,
) {
    OWNER_LEVEL(0),
    WRITER_LEVEL(1),
    READER_LEVEL(2),
}
