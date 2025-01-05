package kr.co.shoppingcart.cart.domain.permissions.vo

import kr.co.shoppingcart.cart.domain.permissions.enums.PermissionLevelEnum

data class PermissionLevel(
    val level: Int,
) {
    fun isOwnerLevel(): Boolean = this.level == PermissionLevelEnum.OWNER_LEVEL.level

    fun isWriterLevel(): Boolean = this.level == PermissionLevelEnum.WRITER_LEVEL.level

    fun isReaderLevel(): Boolean = this.level == PermissionLevelEnum.READER_LEVEL.level

    fun isOverOwnerLevel(): Boolean = this.level <= PermissionLevelEnum.OWNER_LEVEL.level

    fun isOverWriterLevel(): Boolean = this.level <= PermissionLevelEnum.WRITER_LEVEL.level

    fun isOverReaderLevel(): Boolean = this.level <= PermissionLevelEnum.READER_LEVEL.level
}
