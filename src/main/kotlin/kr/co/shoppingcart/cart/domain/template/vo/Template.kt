package kr.co.shoppingcart.cart.domain.template.vo

data class Template(
    val id: TemplateId,
    val name: TemplateName,
    val userId: TemplateOwnerId
) {
    companion object {
        fun toDomain(id: Long, name: String, userId: Long): Template =
            Template(
                id = TemplateId(id),
                name = TemplateName(name),
                userId = TemplateOwnerId(userId)
            )
    }
}