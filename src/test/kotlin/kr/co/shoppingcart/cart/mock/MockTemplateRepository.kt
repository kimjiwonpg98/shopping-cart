package kr.co.shoppingcart.cart.mock

import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import kr.co.shoppingcart.cart.mock.vo.MockTemplate

class MockTemplateRepository : TemplateRepository {
    override fun create(
        name: String,
        userId: Long,
    ): Template = MockTemplate.getTemplate(1)

    override fun getById(id: Long): Template? = MockTemplate.getOptionalTemplate(id, id == TEMPLATE_ID_RETURN_NULL)

    override fun getByIdAndUserId(
        id: Long,
        userId: Long,
    ): Template? = MockTemplate.getOptionalTemplate(id, id == TEMPLATE_ID_RETURN_NULL)

    override fun updateSharedById(
        id: Long,
        isShared: Boolean,
    ) {
    }

    override fun getWithCompletePercent(
        userId: Long,
        page: Long,
        size: Long,
    ): List<TemplateWithCheckedCount> = MockTemplate.getTemplatesWithPercent()

    override fun deleteById(id: Long) {
    }

    companion object {
        private const val TEMPLATE_ID_RETURN_NULL = 100L
    }
}
