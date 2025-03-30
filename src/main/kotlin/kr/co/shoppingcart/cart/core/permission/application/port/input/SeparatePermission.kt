package kr.co.shoppingcart.cart.core.permission.application.port.input

import kr.co.shoppingcart.cart.core.permission.domain.Permission

interface SeparatePermission {
    fun separateOwnerAndOther(permissions: List<Permission>): SeparateOwnerAndOtherRes
}

data class SeparateOwnerAndOtherRes(
    val ownerPermissions: List<Permission>,
    val otherPermissions: List<Permission>,
)
