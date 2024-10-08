package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class GetWithCompletePercentAndPreviewCommand(
    @field:NotNull
    @field:Min(1)
    val userId: Long,
    @field:NotNull
    @field:Min(0)
    val page: Long,
    @field:Min(1)
    @field:NotNull
    val size: Long,
) : SelfValidating<GetWithCompletePercentAndPreviewCommand>() {
    init {
        this.validateSelf()
    }
}
