package kr.co.shoppingcart.cart.api.template

import kr.co.shoppingcart.cart.api.template.dto.response.TemplateResponse
import kr.co.shoppingcart.cart.api.template.dto.response.TemplateWithPercentAndPreviewResponse
import kr.co.shoppingcart.cart.api.template.dto.response.TemplateWithPercentResponse
import kr.co.shoppingcart.cart.domain.template.dto.TemplateWithPercentAndPreviewDto
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
            thumbnailIndex = template.thumbnailIndex.thumbnail,
            createTime = template.createdAt!!.createdAt,
            updateTime = template.updatedAt!!.updatedAt,
        )

    fun toResponseWithPercentAndPreview(
        result: TemplateWithPercentAndPreviewDto,
    ): TemplateWithPercentAndPreviewResponse =
        TemplateWithPercentAndPreviewResponse(
            id = result.id,
            name = result.name,
            userId = result.userId,
            isPinned = result.isPinned,
            isPublic = result.isPublic,
            thumbnailIndex = result.thumbnailIndex,
            createTime = result.createTime,
            updateTime = result.updateTime,
            percent = result.percent,
            preview = result.preview,
        )

    fun toResponseWithPercent(templateWithCheckedCount: TemplateWithCheckedCount): TemplateWithPercentResponse =
        TemplateWithPercentResponse(
            id = templateWithCheckedCount.id.id,
            name = templateWithCheckedCount.name.name,
            userId = templateWithCheckedCount.userId.userId,
            isPublic = templateWithCheckedCount.isPublic.isPublic,
            thumbnailIndex = templateWithCheckedCount.thumbnailIndex.thumbnail,
            createTime = templateWithCheckedCount.createdAt.createdAt,
            updateTime = templateWithCheckedCount.updatedAt.updatedAt,
            percent =
                CalculateUtil.percentInFiveIncrement(
                    templateWithCheckedCount.checkedCount.count + templateWithCheckedCount.unCheckedCount.count,
                    templateWithCheckedCount.checkedCount.count,
                ),
        )
}
