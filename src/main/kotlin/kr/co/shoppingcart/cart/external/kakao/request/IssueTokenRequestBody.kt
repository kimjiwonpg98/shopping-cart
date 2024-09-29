package kr.co.shoppingcart.cart.external.kakao.request

data class IssueTokenRequestBody(
    val grantType: String = "authorization_code",
    val clientId: String,
    val redirectUrl: String = "code",
    val code: String,
) {
    fun toFormDataRequestBody(): String =
        "grant_type=$grantType&client_id=$clientId&redirect_uri=$redirectUrl&code=$code"
}
