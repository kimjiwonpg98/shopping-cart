package kr.co.shoppingcart.cart.database.mysql.template.mapper

import kr.co.shoppingcart.cart.database.mysql.template.entity.TemplateEntity
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.utils.DateUtil.convertZonedDateTimeToSeoulTime

object TemplateEntityMapper {
    fun toDomain(templateEntity: TemplateEntity): Template =
        Template.toDomain(
            id = templateEntity.id!!,
            name = templateEntity.name,
            userId = templateEntity.userId,
            isPublic = templateEntity.isPublic,
            thumbnailIndex = templateEntity.thumbnailIdx,
            createdAt = templateEntity.createdAt?.let { convertZonedDateTimeToSeoulTime(it) },
            updatedAt = templateEntity.updatedAt?.let { convertZonedDateTimeToSeoulTime(it) },
        )

    fun toEntity(template: Template): TemplateEntity =
        TemplateEntity(
            id = template.id.id,
            name = template.name.name,
            userId = template.userId.userId,
            isPublic = template.isPublic.isPublic,
            thumbnailIdx = template.thumbnailIndex.thumbnail,
        )
}
