package kr.co.shoppingcart.cart.mock.vo

import kr.co.shoppingcart.cart.domain.template.command.CreateTemplateCommand
import kr.co.shoppingcart.cart.domain.template.vo.Template
import kr.co.shoppingcart.cart.domain.template.vo.TemplateWithCheckedCount
import kr.co.shoppingcart.cart.fixture.TemplateFixture.DEFAULT_ID
import kr.co.shoppingcart.cart.fixture.TemplateFixture.DEFAULT_NAME
import kr.co.shoppingcart.cart.fixture.TemplateFixture.DEFAULT_THUMBNAIL
import kr.co.shoppingcart.cart.fixture.TemplateFixture.DEFAULT_USER_ID
import kr.co.shoppingcart.cart.fixture.TemplateFixture.IS_PINNED
import kr.co.shoppingcart.cart.fixture.TemplateFixture.IS_UNPINNED
import kr.co.shoppingcart.cart.fixture.TemplateFixture.PRIVATE
import java.time.ZonedDateTime

object MockTemplate {
    fun getTemplate(
        i: Long,
        name: String = DEFAULT_NAME,
        userId: Long = DEFAULT_USER_ID,
        isPublic: Boolean = PRIVATE,
        thumbnailIndex: Int = DEFAULT_THUMBNAIL,
    ): Template =
        Template.toDomain(
            i,
            name,
            userId,
            isPublic,
            IS_PINNED,
            thumbnailIndex,
            null,
            null,
        )

    fun getTemplateWithPercent(i: Long): TemplateWithCheckedCount =
        TemplateWithCheckedCount.toDomain(
            i,
            DEFAULT_NAME,
            DEFAULT_USER_ID,
            PRIVATE,
            IS_PINNED,
            DEFAULT_THUMBNAIL,
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
            DEFAULT_NAME,
            DEFAULT_USER_ID,
            isPublic,
            IS_PINNED,
            DEFAULT_THUMBNAIL,
            null,
            null,
        )

    fun getOptionalTemplate(
        i: Long,
        flag: Boolean,
    ): Template? = if (!flag) getTemplate(i) else null

    fun getTemplatesWithPercent(): List<TemplateWithCheckedCount> {
        val templates = mutableListOf<TemplateWithCheckedCount>()
        for (i in 0..5) {
            templates.add(getTemplateWithPercent(i.toLong()))
        }
        return templates
    }

    fun getTemplateByCreate(createTemplateCommand: CreateTemplateCommand): Template =
        Template.toDomain(
            DEFAULT_ID,
            createTemplateCommand.name,
            createTemplateCommand.userId,
            PRIVATE,
            IS_PINNED,
            DEFAULT_THUMBNAIL,
            null,
            null,
        )

    fun getTemplates(
        name: String = DEFAULT_NAME,
        userId: Long = DEFAULT_USER_ID,
        isPublic: Boolean = PRIVATE,
        isPinned: Boolean = IS_UNPINNED,
        thumbnailIndex: Int = DEFAULT_THUMBNAIL,
        count: Int = 10,
    ): List<Template> {
        val result = mutableListOf<Template>()

        for (i in 0 until count) {
            result.add(
                Template.toDomain(
                    (i + 1).toLong(),
                    name,
                    userId,
                    isPublic,
                    isPinned,
                    thumbnailIndex,
                    null,
                    null,
                ),
            )
        }
        return result
    }
}
