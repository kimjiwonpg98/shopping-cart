package kr.co.shoppingcart.cart.external.kakao

import kr.co.shoppingcart.cart.external.kakao.response.GetKakaoUserByAuthTokenResponse

interface KakaoClient {
    fun getTokenByGrantCode(grantCode: String): String?

    fun getUserByAuthToken(authToken: String): GetKakaoUserByAuthTokenResponse?

    fun unlinkUser(authIdentifier: String): Long?
}
