package kr.co.shoppingcart.cart.external.kakao.response

data class IssueTokenResponseBody(
    val token_type: String,
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val refresh_token_expires_in: Int,
)
