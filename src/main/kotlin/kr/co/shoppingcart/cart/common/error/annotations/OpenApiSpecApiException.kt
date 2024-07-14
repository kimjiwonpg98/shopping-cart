package kr.co.shoppingcart.cart.common.error.annotations

import kr.co.shoppingcart.cart.common.error.model.ExceptionCode

annotation class OpenApiSpecApiException(
    val values: Array<ExceptionCode> = [],
)
