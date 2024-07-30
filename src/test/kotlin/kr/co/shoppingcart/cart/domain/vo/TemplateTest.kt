package kr.co.shoppingcart.cart.domain.vo

import kr.co.shoppingcart.cart.domain.template.vo.Template
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

class TemplateTest {
    @Test
    fun `값들을 넣으면 Template 객체로 응답한다`() {
        // when
        val template: Template =
            Template.toDomain(
                ID,
                NAME,
                USER_ID,
                IS_PUBLIC,
                createdAt,
                updatedAt,
            )

        // then
        assertEquals(template.id.id, ID)
        assertEquals(template.name.name, NAME)
        assertEquals(template.userId.userId, USER_ID)
        assertEquals(template.isPublic.isPublic, IS_PUBLIC)
        assertNotNull(template.createdAt)
        assertNotNull(template.updatedAt)
        assertEquals(template.createdAt!!.createdAt, createdAt)
        assertEquals(template.updatedAt!!.updatedAt, updatedAt)
    }

    @Test
    fun `createdAt, updatedAt이 없어도 template 객체로 응답한다`() {
        // when
        val template =
            Template.toDomain(
                ID,
                NAME,
                USER_ID,
                IS_PUBLIC,
                null,
                null,
            )

        // then
        assertEquals(template::class.java, Template::class.java)
        assertEquals(template.id.id, ID)
        assertEquals(template.name.name, NAME)
        assertEquals(template.userId.userId, USER_ID)
        assertEquals(template.isPublic.isPublic, IS_PUBLIC)
    }

    @Test
    fun `createdAt, updatedAt이 null이면 응답도 null이다`() {
        // when
        val template =
            Template.toDomain(
                ID,
                NAME,
                USER_ID,
                IS_PUBLIC,
                null,
                null,
            )

        // then
        assertNull(template.createdAt)
        assertNull(template.updatedAt)
    }

    companion object {
        private const val ID: Long = 1L
        private const val NAME: String = "test"
        private const val USER_ID: Long = 1L
        private const val IS_PUBLIC: Boolean = true
        private val createdAt: ZonedDateTime = ZonedDateTime.now()
        private val updatedAt: ZonedDateTime = ZonedDateTime.now()
    }
}
