package kr.co.shoppingcart.cart.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("계산 유틸 테스트")
class CalculateUtilTest {
    @Test
    fun `전체에서 사용한 부분의 퍼센트로 응답한다`() {
        val totalCount = 10L
        val targetCount = 5L

        val percent = CalculateUtil.percentInFiveIncrement(totalCount, targetCount)

        assertEquals(50, percent)
    }

    @Test
    fun `소수점으로 나눠졌을 때에도 5단위로 응답한다`() {
        val totalCount = 14L
        val targetCount = 5L

        val percent = CalculateUtil.percentInFiveIncrement(totalCount, targetCount)

        assertEquals(35, percent)
    }

    @Test
    fun `타켓이 0이면 결과값은 0이다`() {
        val totalCount = 14L
        val targetCount = 0L

        val percent = CalculateUtil.percentInFiveIncrement(totalCount, targetCount)

        assertEquals(0, percent)
    }

    @Test
    fun `타켓이 전체와 같으면 결과값은 100이다`() {
        val totalCount = 14L
        val targetCount = 14L

        val percent = CalculateUtil.percentInFiveIncrement(totalCount, targetCount)

        assertEquals(100, percent)
    }
}
