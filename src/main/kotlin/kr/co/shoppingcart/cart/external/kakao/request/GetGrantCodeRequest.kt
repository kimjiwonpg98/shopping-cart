package kr.co.shoppingcart.cart.external.kakao.request

import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

data class GetGrantCodeRequest(
    val clientId: String,
    val redirectUri: String,
    val responseType: String = "code",
    val scope: List<String>,
) {
    fun toQueryParams(): MultiValueMap<String, String> {
        val queryMap = LinkedMultiValueMap<String, String>()
        queryMap.add("scope", scope.toString())
        queryMap.add("client_id", clientId)
        queryMap.add("redirect_uri", redirectUri)
        queryMap.add("response_type", responseType)
        return queryMap
    }
}
