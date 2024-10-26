package kr.co.shoppingcart.cart.mock.vo

import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
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
            1,
            null,
            null,
        )

    fun getTemplateWithPercent(i: Long): TemplateWithCheckedCount =
        TemplateWithCheckedCount.toDomain(
            i,
            "test",
            1L,
            true,
            1,
            0,
            0,
            ZonedDateTime.now(),
            ZonedDateTime.now(),
        )

    fun getTemplateByPublic(
        i: Long,
        isPublic: Boolean,
    ): Template =
        Template.toDomain(
            i,
            "test",
            1L,
            isPublic,
            1,
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

    fun getTemplatesWithPercent(): List<TemplateWithCheckedCount> {
        val templates = mutableListOf<TemplateWithCheckedCount>()
        for (i in 0..5) {
            templates.add(getTemplateWithPercent(i.toLong()))
        }
        return templates
    }

    fun getTemplateByCreate(createTemplateCommand: CreateTemplateCommand): Template =
        Template.toDomain(
            1,
            createTemplateCommand.name,
            createTemplateCommand.userId,
            false,
            1,
            null,
            null,
        )
}
