package kr.co.shoppingcart.cart.domain.template

import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount

interface TemplateRepository {
    fun create(
        name: String,
        thumbnailIndex: Int,
        userId: Long,
    ): Template

    fun getById(id: Long): Template?

    fun getByIdWithPercent(id: Long): TemplateWithCheckedCount?

    fun getByIdAndUserId(
        id: Long,
        userId: Long,
    ): Template?

    fun updateSharedById(
        id: Long,
        isShared: Boolean,
    ): Template

    fun update(
        id: Long,
        name: String?,
        thumbnailIndex: Int?,
    ): Template

    fun getWithCompletePercent(userId: Long): List<TemplateWithCheckedCount>

    fun deleteById(id: Long)

    fun deleteByIds(id: List<Long>)

    fun getByUserIdForDefaultName(userId: Long): Template?
}
