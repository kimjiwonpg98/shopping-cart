package kr.co.shoppingcart.cart.mock

import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import kr.co.shoppingcart.cart.fixture.TemplateFixture.DEFAULT_ID
import kr.co.shoppingcart.cart.mock.vo.MockTemplate

class MockTemplateRepository : TemplateRepository {
    override fun create(
        name: String,
        thumbnailIndex: Int,
        userId: Long,
    ): Template = MockTemplate.getTemplate(1)

    override fun getById(id: Long): Template? = MockTemplate.getOptionalTemplate(id, id == TEMPLATE_ID_RETURN_NULL)

    override fun getByIdWithPercent(id: Long): TemplateWithCheckedCount? {
        TODO("Not yet implemented")
    }

    override fun getByIdAndUserId(
        id: Long,
        userId: Long,
    ): Template? = MockTemplate.getOptionalTemplate(id, id == TEMPLATE_ID_RETURN_NULL)

    override fun updateSharedById(
        id: Long,
        isShared: Boolean,
    ): Template = MockTemplate.getTemplate(1)

    override fun update(
        id: Long,
        name: String?,
        thumbnailIndex: Int?,
    ): Template = MockTemplate.getTemplate(1)

    override fun getWithCompletePercent(userId: Long): List<TemplateWithCheckedCount> =
        MockTemplate.getTemplatesWithPercent()

    override fun deleteById(id: Long) {
    }

    override fun deleteByIds(id: List<Long>) {
        TODO("Not yet implemented")
    }

    override fun getByUserIdForDefaultName(userId: Long): Template? = MockTemplate.getOptionalTemplate(DEFAULT_ID, true)

    override fun getCountByUserId(userId: Long): Long {
        TODO("Not yet implemented")
    }

    companion object {
        private const val TEMPLATE_ID_RETURN_NULL = 100L
    }
}
