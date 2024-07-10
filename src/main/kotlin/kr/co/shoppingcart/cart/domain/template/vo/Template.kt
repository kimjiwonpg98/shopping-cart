package kr.co.shoppingcart.cart.domain.template.vo

import java.time.ZonedDateTime

data class Template(
    val id: TemplateId,
    val name: TemplateName,
    val userId: TemplateOwnerId,
    val createdAt: TemplateCreatedAt?,
    val updatedAt: TemplateUpdatedAt?,
) {
    companion object {
        fun toDomain(
            id: Long,
            name: String,
            userId: Long,
            createdAt: ZonedDateTime?,
            updatedAt: ZonedDateTime?,
        ): Template =
            Template(
                id = TemplateId(id),
                name = TemplateName(name),
                userId = TemplateOwnerId(userId),
                createdAt = createdAt?.let { TemplateCreatedAt(it) },
                updatedAt = updatedAt?.let { TemplateUpdatedAt(it) }
            )
    }
}