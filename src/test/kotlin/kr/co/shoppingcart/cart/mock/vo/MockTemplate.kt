package kr.co.shoppingcart.cart.mock.vo

import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import java.time.ZonedDateTime

object MockTemplate {
    fun getTemplate(i: Long): Template =
        Template.toDomain(
            i,
            "test",
            1L,
            true,
            null,
            null,
        )

    fun getOptionalTemplate(
        i: Long,
        flag: Boolean,
    ): Template? = if (!flag) getTemplate(i) else null

    fun getTemplates(): List<Template> {
        val templates = mutableListOf<Template>()
        for (i in 0..5) {
            templates.add(getTemplate(i.toLong()))
        }
        return templates
    }

    fun getTemplateWithPercent(i: Long): TemplateWithCheckedCount =
        TemplateWithCheckedCount.toDomain(
            i,
            "test",
            1L,
            true,
            10,
            10,
            ZonedDateTime.now(),
            ZonedDateTime.now(),
        )

    fun getTemplatesWithPercent(): List<TemplateWithCheckedCount> {
        val templates = mutableListOf<TemplateWithCheckedCount>()
        for (i in 0..5) {
            templates.add(getTemplateWithPercent(i.toLong()))
        }
        return templates
    }
}
