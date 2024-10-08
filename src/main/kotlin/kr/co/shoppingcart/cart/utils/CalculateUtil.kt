package kr.co.shoppingcart.cart.utils

import kotlin.math.roundToInt

object CalculateUtil {
    fun percentInFiveIncrement(
        totalCount: Long,
        targetCount: Long,
    ): Int {
        if (totalCount == 0L && targetCount == 0L) return 0

        val ratio = targetCount.toDouble() / totalCount * 100
        return (ratio / 5).roundToInt() * 5
    }
}
