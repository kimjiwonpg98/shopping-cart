package kr.co.shoppingcart.cart.domain.vo

import kr.co.shoppingcart.cart.domain.basket.vo.Basket
import kr.co.shoppingcart.cart.domain.category.vo.Category
import kr.co.shoppingcart.cart.domain.template.vo.Template
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import java.time.ZonedDateTime

class BasketTest {
    @Test
    fun `값들을 넣으면 Basket 객체로 응답한다`() {
        // when
        val basket: Basket =
            Basket.toDomain(
                name = name,
                checked = checked,
                count = count,
                category = category,
                template = template,
                createdTime = createdAt,
                updatedTime = updatedAt,
                templateId = null,
                categoryId = null,
            )

        // then
        assertEquals(basket.name.name, name)
        assertEquals(basket.checked.checked, checked)
        assertEquals(basket.count.count, count)
        assertNotNull(basket.createTime)
        assertNotNull(basket.updateTime)
        assertEquals(basket.createTime!!.createdAt, createdAt)
        assertEquals(basket.updateTime!!.updatedAt, updatedAt)
    }

    @Test
    fun `createdAt, updatedAt이 없어도 basket 객체로 응답한다`() {
        // when
        val basket: Basket =
            Basket.toDomain(
                name = name,
                checked = checked,
                count = count,
                category = category,
                template = template,
                createdTime = createdAt,
                updatedTime = updatedAt,
                templateId = null,
                categoryId = null,
            )

        // then
        assertEquals(basket.name.name, name)
        assertEquals(basket.checked.checked, checked)
        assertEquals(basket.count.count, count)
        assertNotNull(basket.createTime)
        assertNotNull(basket.updateTime)
        assertEquals(basket.createTime!!.createdAt, createdAt)
        assertEquals(basket.updateTime!!.updatedAt, updatedAt)
    }

    @Test
    fun `createdAt, updatedAt이 null이면 응답도 null이다`() {
        // when
        val basket: Basket =
            Basket.toDomain(
                name = name,
                checked = checked,
                count = count,
                category = category,
                template = template,
                createdTime = null,
                updatedTime = null,
                templateId = null,
                categoryId = null,
            )

        assertNull(basket.createTime)
        assertNull(basket.updateTime)
    }

    companion object {
        private val name: String = "test"
        private val count: Long = 1L
        private val checked: Boolean = true
        private val category = Category.toDomain(1L, "category")
        private val template = Template.toDomain(1L, "template", 1L, true, null, null)
        private val createdAt: ZonedDateTime = ZonedDateTime.now()
        private val updatedAt: ZonedDateTime = ZonedDateTime.now()
    }
}
