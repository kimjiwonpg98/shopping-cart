package kr.co.shoppingcart.cart.external.kakao

import kr.co.shoppingcart.cart.external.kakao.response.GetKakaoUserByAuthTokenResponse

interface KakaoLoginClient {
    fun getTokenByGrantCode(grantCode: String): String?

    fun getUserByAuthToken(authToken: String): GetKakaoUserByAuthTokenResponse?
}
