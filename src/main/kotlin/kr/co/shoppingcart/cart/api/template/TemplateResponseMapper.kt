package kr.co.shoppingcart.cart.api.template

import kr.co.shoppingcart.cart.api.template.dto.response.TemplateResponse
import kr.co.shoppingcart.cart.api.template.dto.response.TemplateWithPercentResponse
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import kr.co.shoppingcart.cart.utils.CalculateUtil

object TemplateResponseMapper {
    fun toResponse(template: Template): TemplateResponse =
        TemplateResponse(
            id = template.id.id,
            name = template.name.name,
            userId = template.userId.userId,
            isPublic = template.isPublic.isPublic,
            createTime = template.createdAt!!.createdAt,
            updateTime = template.updatedAt!!.updatedAt,
        )

    fun toResponseWithPercent(
        templateWithCheckedCount: TemplateWithCheckedCount,
        preview: List<String>,
    ): TemplateWithPercentResponse =
        TemplateWithPercentResponse(
            id = templateWithCheckedCount.id.id,
            name = templateWithCheckedCount.name.name,
            userId = templateWithCheckedCount.userId.userId,
            isPublic = templateWithCheckedCount.isPublic.isPublic,
            createTime = templateWithCheckedCount.createdAt.createdAt,
            updateTime = templateWithCheckedCount.updatedAt.updatedAt,
            percent =
                CalculateUtil.percentInFiveIncrement(
                    templateWithCheckedCount.checkedCount.count + templateWithCheckedCount.unCheckedCount.count,
                    templateWithCheckedCount.checkedCount.count,
                ),
            preview = preview,
        )
}
