package kr.co.shoppingcart.cart.domain.template.dto

import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import kr.co.shoppingcart.cart.utils.CalculateUtil
import java.time.ZonedDateTime

data class TemplateWithPercentAndPreviewDto(
    val id: Long,
    val name: String,
    val userId: Long,
    val percent: Int,
    val isPinned: Boolean,
    val isPublic: Boolean,
    val thumbnailIndex: Int,
    val createTime: ZonedDateTime,
    val updateTime: ZonedDateTime,
    val preview: List<String>,
) {
    companion object {
        fun of(
            template: TemplateWithCheckedCount,
            basketNames: List<String>,
        ): TemplateWithPercentAndPreviewDto =
            TemplateWithPercentAndPreviewDto(
                id = template.id.id,
                name = template.name.name,
                userId = template.userId.userId,
                isPinned = template.isPinned.isPinned,
                isPublic = template.isPublic.isPublic,
                thumbnailIndex = template.thumbnailIndex.thumbnail,
                createTime = template.createdAt.createdAt,
                updateTime = template.updatedAt.updatedAt,
                percent =
                    CalculateUtil.percentInFiveIncrement(
                        template.checkedCount.count + template.unCheckedCount.count,
                        template.checkedCount.count,
                    ),
                preview = basketNames,
            )
    }
}
