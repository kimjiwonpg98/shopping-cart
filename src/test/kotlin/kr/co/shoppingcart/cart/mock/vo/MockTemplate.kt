package kr.co.shoppingcart.cart.mock.vo

import kr.co.shoppingcart.cart.domain.template.vo.Template
import java.time.ZonedDateTime

object MockTemplate {
    fun getTemplate(i: Long): Template =
        Template.toDomain(
            i,
            "test",
            1L,
            true,
            ZonedDateTime.now(),
            ZonedDateTime.now(),
        )

    fun getTemplates(): List<Template> {
        val templates = mutableListOf<Template>()
        for (i in 0..5) {
            templates.add(getTemplate(i.toLong()))
        }
        return templates
    }
}
