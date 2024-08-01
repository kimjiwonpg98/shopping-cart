package kr.co.shoppingcart.cart.utils

import kotlin.math.roundToInt

object CalculateUtil {
    fun percentInFiveIncrement(
        totalCount: Long,
        targetCount: Long,
    ): Int {
        val ratio = targetCount.toDouble() / totalCount * 100
        return (ratio / 5).roundToInt() * 5
    }
}
