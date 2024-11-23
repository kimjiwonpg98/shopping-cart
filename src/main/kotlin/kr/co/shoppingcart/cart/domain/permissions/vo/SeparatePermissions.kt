package kr.co.shoppingcart.cart.domain.permissions.vo

data class SeparatePermissions(
    val ownerPermissions: List<Permissions>,
    val otherPermissions: List<Permissions>,
)
