package kr.co.shoppingcart.cart.domain.template

import kr.co.shoppingcart.cart.domain.template.vo.Template

interface TemplateRepository {
    fun create(name: String, userId: Long)
    fun getById(id: Long): Template?
    fun getByIdAndUserId(id: Long, userId: Long): Template?
    fun updateSharedById(id: Long, isShared: Boolean)
}