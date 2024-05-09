package kr.co.shoppingcart.common.error.translators

import kr.co.shoppingcart.common.error.model.ExceptionCode
import kr.co.shoppingcart.common.error.model.ExceptionResponseBody

interface ExceptionCodeTranslator {
    fun translate(key: ExceptionCode): ExceptionResponseBody
}