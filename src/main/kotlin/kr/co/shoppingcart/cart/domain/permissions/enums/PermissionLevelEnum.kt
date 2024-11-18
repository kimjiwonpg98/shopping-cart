package kr.co.shoppingcart.cart.domain.permissions.enums

enum class PermissionLevelEnum(
    val level: Int,
) {
    OWNER_LEVEL(0),
    WRITER_LEVEL(1),
    READER_LEVEL(2),
}
