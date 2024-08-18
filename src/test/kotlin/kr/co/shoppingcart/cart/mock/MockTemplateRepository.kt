package kr.co.shoppingcart.cart.mock

import kr.co.shoppingcart.cart.domain.template.TemplateRepository
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount

class MockTemplateRepository : TemplateRepository {
    override fun create(
        name: String,
        userId: Long,
    ): Template {
        TODO("Not yet implemented")
    }

    override fun getById(id: Long): Template? {
        TODO("Not yet implemented")
    }

    override fun getByIdAndUserId(
        id: Long,
        userId: Long,
    ): Template? {
        TODO("Not yet implemented")
    }

    override fun updateSharedById(
        id: Long,
        isShared: Boolean,
    ) {
        TODO("Not yet implemented")
    }

    override fun getWithCompletePercent(
        userId: Long,
        page: Long,
        size: Long,
    ): List<TemplateWithCheckedCount> {
        TODO("Not yet implemented")
    }
}
