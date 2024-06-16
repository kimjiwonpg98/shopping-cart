package kr.co.shoppingcart.cart.domain.template

interface TemplateRepository {
    fun create(name: String, userId: Long)
}