package kr.co.shoppingcart.cart.domain.vo

import kr.co.shoppingcart.cart.domain.category.vo.Category
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class CategoryTest {
    @Test
    fun `값들을 넣으면 Category 객체로 응답한다`() {
        val category = Category.toDomain(ID, NAME)

        assertEquals(category.id.id, ID)
        assertEquals(category.name.name, NAME)
    }

    companion object {
        private const val ID = 1L
        private const val NAME = "이름"
    }
}
