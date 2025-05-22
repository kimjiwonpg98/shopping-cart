package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class PinnedTemplateCommand(
    @field:NotNull
    @field:Min(1)
    val templateId: Long,
    @field:NotNull
    @field:Min(1)
    val userId: Long,
)
