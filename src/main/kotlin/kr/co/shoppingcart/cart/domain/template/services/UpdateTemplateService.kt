package kr.co.shoppingcart.cart.domain.template.services

import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import org.springframework.stereotype.Component

@Component
class UpdateTemplateService(
    private val templateRepository: TemplateRepository,
) {
    fun updateSharedById(
        id: Long,
        isShared: Boolean,
    ) = templateRepository.updateSharedById(
        id,
        isShared,
    )

    fun update(
        templateId: Long,
        name: String?,
        thumbnailIndex: Int?,
    ) = templateRepository.update(
        templateId,
        name,
        thumbnailIndex,
    )
}
