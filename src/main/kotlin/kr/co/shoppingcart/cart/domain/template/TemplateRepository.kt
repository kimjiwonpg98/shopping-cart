package kr.co.shoppingcart.cart.domain.template

import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount

interface TemplateRepository {
    fun create(
        name: String,
        userId: Long,
    ): Template

    fun getById(id: Long): Template?

    fun getByIdAndUserId(
        id: Long,
        userId: Long,
    ): Template?

    fun updateSharedById(
        id: Long,
        isShared: Boolean,
    )

    fun getWithCompletePercent(
        userId: Long,
        page: Long,
        size: Long,
    ): List<TemplateWithCheckedCount>

    fun deleteById(id: Long)
}
