package kr.co.shoppingcart.cart.database.mysql.template.mapper

import kr.co.shoppingcart.cart.database.mysql.template.jdbc.dto.TemplateWithCheckedCountDto
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import kr.co.shoppingcart.cart.utils.DateUtil.convertZonedDateTimeToSeoulTime

object TemplateWithCheckedCountEntityMapper {
    fun toDomain(templateEntity: TemplateWithCheckedCountDto): TemplateWithCheckedCount =
        TemplateWithCheckedCount.toDomain(
            unCheckedCount = templateEntity.unCheckedCount,
            checkedCount = templateEntity.checkedCount,
            id = templateEntity.id,
            name = templateEntity.name,
            userId = templateEntity.userId,
            isPublic = templateEntity.isPublic,
            createdAt = convertZonedDateTimeToSeoulTime(templateEntity.createdAt),
            updatedAt = convertZonedDateTimeToSeoulTime(templateEntity.updatedAt),
        )
}
