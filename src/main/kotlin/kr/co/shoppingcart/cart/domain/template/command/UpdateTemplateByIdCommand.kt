package kr.co.shoppingcart.cart.domain.template.command

import jakarta.validation.constraints.NotNull

data class UpdateTemplateByIdCommand(
    @field:NotNull
    val templateId: Long,
    @field:NotNull
    val userId: Long,
    @field:NotNull
    val name: String?,
    @field:NotNull
    val thumbnailIndex: Int?,
)
