package kr.co.shoppingcart.cart.external.kakao.response

data class GetKakaoUserByAuthTokenResponse(
    val id: Long,
    val properties: List<String>?,
)
