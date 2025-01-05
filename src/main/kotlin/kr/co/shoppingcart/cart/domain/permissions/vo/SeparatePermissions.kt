package kr.co.shoppingcart.cart.domain.permissions.vo

data class SeparatePermissions(
    val ownerPermissions: List<Permission>,
    val otherPermissions: List<Permission>,
) {
    companion object {
        fun toDomain(permissions: List<Permission>): SeparatePermissions =
            permissions.partition { it.level.isOwnerLevel() }.let {
                SeparatePermissions(
                    ownerPermissions = it.first,
                    otherPermissions = it.second,
                )
            }
    }
}
