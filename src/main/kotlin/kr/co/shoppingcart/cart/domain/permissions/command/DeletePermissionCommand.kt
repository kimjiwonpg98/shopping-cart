package kr.co.shoppingcart.cart.domain.permissions.command

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class DeletePermissionCommand(
    @field:Min(1)
    @field:NotNull
    val userId: Long,
    @field:Min(1)
    @field:NotNull
    val templateId: Long,
) : SelfValidating<DeletePermissionCommand>() {
    init {
        validateSelf()
    }
}
