package kr.co.shoppingcart.common.error.model

data class ExceptionResponseBody (
    val code: String,
    val message: String = "",
    val title: String
)