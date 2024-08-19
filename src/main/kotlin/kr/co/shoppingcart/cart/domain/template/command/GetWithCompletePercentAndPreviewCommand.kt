package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class GetWithCompletePercentAndPreviewCommand(
    @field:NotNull
    val userId: Long,
    @field:NotNull
    val page: Long,
    @field:NotNull
    val size: Long,
) : SelfValidating<GetWithCompletePercentAndPreviewCommand>() {
    init {
        this.validateSelf()
    }
}
