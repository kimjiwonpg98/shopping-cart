package kr.co.shoppingcart.cart.api.template

import kr.co.shoppingcart.cart.api.template.dto.out.TemplateResponse
import kr.co.shoppingcart.cart.domain.template.vo.Template

object TemplateResponseMapper {
    fun toDomain(template: Template): TemplateResponse = TemplateResponse(
        id = template.id.id,
        name = template.name.name,
        userId = template.userId.userId,
        createTime = template.createdAt!!.createdAt,
        updateTime = template.updatedAt!!.updatedAt
    )
}