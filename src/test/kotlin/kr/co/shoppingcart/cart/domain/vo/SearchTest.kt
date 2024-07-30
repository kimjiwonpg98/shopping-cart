package kr.co.shoppingcart.cart.domain.vo

import kr.co.shoppingcart.cart.domain.search.vo.Search
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class SearchTest {
    @Test
    fun `값을 넣으면 Search 객체가 응답갑으로 나온다`() {
        val search: Search =
            Search.toDomain(
                NAME,
                CATEGORY,
            )

        assertEquals(search.name.name, NAME)
        assertEquals(search.category.category, CATEGORY)
    }

    companion object {
        private const val NAME = "이름"
        private const val CATEGORY = "카테고리"
    }
}
