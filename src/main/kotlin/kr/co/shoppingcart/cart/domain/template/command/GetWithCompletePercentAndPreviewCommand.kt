package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import kr.co.shoppingcart.cart.common.SelfValidating

data class GetWithCompletePercentAndPreviewCommand(
    @field:NotNull
    @field:Min(1)
    val userId: Long,
    val previewCount: Int = 3,
) : SelfValidating<GetWithCompletePercentAndPreviewCommand>() {
    init {
        this.validateSelf()
    }
}
