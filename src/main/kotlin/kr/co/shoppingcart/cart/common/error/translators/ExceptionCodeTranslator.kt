package kr.co.shoppingcart.cart.common.error.translators

import kr.co.shoppingcart.cart.common.error.model.ExceptionCode
import kr.co.shoppingcart.cart.common.error.model.ExceptionResponseBody

interface ExceptionCodeTranslator {
    fun translate(
        key: ExceptionCode,
        detail: String? = null,
    ): ExceptionResponseBody
}
