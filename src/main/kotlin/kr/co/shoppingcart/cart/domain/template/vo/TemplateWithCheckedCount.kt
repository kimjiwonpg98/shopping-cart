package kr.co.shoppingcart.cart.domain.template.vo

import java.time.ZonedDateTime

data class TemplateWithCheckedCount(
    val unCheckedCount: TemplateUnCheckedCount,
    val checkedCount: TemplateCheckedCount,
    val id: TemplateId,
    val name: TemplateName,
    val userId: TemplateOwnerId,
    val isPublic: TemplateIsPublic,
    val thumbnailIndex: TemplateThumbnailIndex,
    val createdAt: TemplateCreatedAt,
    val updatedAt: TemplateUpdatedAt,
) {
    companion object {
        fun toDomain(
            id: Long,
            name: String,
            userId: Long,
            isPublic: Boolean,
            thumbnailIndex: Int,
            checkedCount: Long,
            unCheckedCount: Long,
            createdAt: ZonedDateTime,
            updatedAt: ZonedDateTime,
        ): TemplateWithCheckedCount =
            TemplateWithCheckedCount(
                id = TemplateId(id),
                name = TemplateName(name),
                userId = TemplateOwnerId(userId),
                isPublic = TemplateIsPublic(isPublic),
                thumbnailIndex = TemplateThumbnailIndex(thumbnailIndex),
                checkedCount = TemplateCheckedCount(checkedCount),
                unCheckedCount = TemplateUnCheckedCount(unCheckedCount),
                createdAt = TemplateCreatedAt(createdAt),
                updatedAt = TemplateUpdatedAt(updatedAt),
            )
    }
}
